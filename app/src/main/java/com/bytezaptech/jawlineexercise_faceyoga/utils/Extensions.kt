package com.bytezaptech.jawlineexercise_faceyoga.utils

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.CustomToastBinding
import com.bytezaptech.jawlineexercise_faceyoga.databinding.MessageDialogBinding
import com.bytezaptech.jawlineexercise_faceyoga.databinding.QuitDialogFragmentBinding
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun ViewPager2.setCurrentItem(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxWidth: Int = width // Default value taken from getWidth() from ViewPager2 view
) {
    val pxToDrag: Int = pagePxWidth * (item - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator, isReverse: Boolean) {
            beginFakeDrag()
        }

        override fun onAnimationStart(p0: Animator) {
            //ignore
        }

        override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
            endFakeDrag()
        }

        override fun onAnimationEnd(p0: Animator) {
            //ignore
        }

        override fun onAnimationCancel(p0: Animator) { /* Ignored */
        }

        override fun onAnimationRepeat(p0: Animator) { /* Ignored */
        }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}

fun Toast.showError(
    toast: Toast,
    context: Context,
    parent: ViewGroup,
    message: String,
) {
    val toastBinding: CustomToastBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_toast, parent, false)
    toastBinding.tvMessage.text = message

    toastBinding.root.setCardBackgroundColor(
        ResourcesCompat.getColor(
            context.resources,
            R.color.transparent_red,
            context.theme
        )
    )
    toastBinding.toastRl.setBackgroundResource(R.drawable.stroke_shape)
    toastBinding.icFm.setBackgroundResource(R.drawable.rectangle_shape)
    toastBinding.icFm.backgroundTintList =
        ResourcesCompat.getColorStateList(context.resources, R.color.red, context.theme)
    toastBinding.icIv.visibility = View.GONE
    toastBinding.icCancelIv.visibility = View.VISIBLE

    toast.setGravity(Gravity.BOTTOM, 0, 0)
    toast.view = toastBinding.root
    toast.duration = Toast.LENGTH_SHORT
    toast.show()
}

fun Toast.showSuccess(
    toast: Toast,
    context: Context,
    parent: ViewGroup,
    message: String,
) {
    val toastBinding: CustomToastBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_toast, parent, false)
    toastBinding.tvMessage.text = message

    toast.setGravity(Gravity.BOTTOM, 0, 0)
    toast.view = toastBinding.root
    toast.duration = Toast.LENGTH_SHORT
    toast.show()
}

fun Calendar.getFormattedDate(time: Long, format: String): String {
    val date = Date(time)
    val cal = Calendar.getInstance()
    cal.timeInMillis = date.time

    val spf = SimpleDateFormat(format, Locale.getDefault())
    return spf.format(cal.time).toString().uppercase()
}

fun showMessageDialog(
    context: Context,
    title: String,
    message: String,
    btnText: String
): AlertDialog {
    val builder = AlertDialog.Builder(context, R.style.MessageDialog)
    val dialog = builder.create()
    val binding: MessageDialogBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.message_dialog, null, false)
    binding.tvTitle.text = title
    binding.tvMsg.text = message
    binding.tvOkBtn.text = btnText
    dialog.setView(binding.root)
    dialog.setCancelable(false)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    binding.tvOkBtn.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
    return dialog
}

fun somethingWentWrong(
    activity: Activity,
    navController: NavController,
    title: String,
    message: String,
    btnText: String
): AlertDialog {
    val builder = AlertDialog.Builder(activity, R.style.MessageDialog)
    val dialog = builder.create()
    val binding: MessageDialogBinding =
        DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.message_dialog, null, false)
    binding.tvTitle.text = title
    binding.tvMsg.text = message
    binding.tvOkBtn.text = btnText
    dialog.setView(binding.root)
    dialog.setCancelable(false)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    binding.tvOkBtn.setOnClickListener {
        navController.popBackStack()
        dialog.dismiss()
    }
    dialog.show()
    return dialog
}

fun showAlertDialog(
    context: Context,
    message: String,
    quitBtnTxt: String,
    cancelBtnTxt: String
): AlertDialog {
    val builder = AlertDialog.Builder(context, R.style.MessageDialog)
    val dialog = builder.create()
    val binding: QuitDialogFragmentBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.quit_dialog_fragment,
        null,
        false
    )
    binding.msgTv.text = message
    binding.quitBtn.text = quitBtnTxt
    binding.cancelBtn.text = cancelBtnTxt
    dialog.setView(binding.root)
    dialog.setCancelable(false)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    binding.quitBtn.setOnClickListener {
        dialog.dismiss()
    }

    binding.cancelBtn.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
    return dialog
}

fun Fragment.findNavControllerSafety(currentId: Int): NavController? {
    try {
        val controller = NavHostFragment.findNavController(this)

        return if (controller.currentDestination?.id != currentId) null else controller
    } catch (e: Exception) {
        return null
    }
}