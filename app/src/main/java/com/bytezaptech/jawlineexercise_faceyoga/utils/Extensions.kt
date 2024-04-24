package com.bytezaptech.jawlineexercise_faceyoga.utils

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.CustomToastBinding
import com.google.android.material.snackbar.Snackbar

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

    toastBinding.root.setCardBackgroundColor(ResourcesCompat.getColor(context.resources, R.color.transparent_red, context.theme))
    toastBinding.toastRl.setBackgroundResource(R.drawable.stroke_shape)
    toastBinding.icFm.setBackgroundResource(R.drawable.rectangle_shape)
    toastBinding.icFm.backgroundTintList = ResourcesCompat.getColorStateList(context.resources, R.color.red, context.theme)
    toastBinding.icIv.visibility = View.GONE
    toastBinding.icCancelIv.visibility = View.VISIBLE
    @RequiresApi(Build.VERSION_CODES.P)
    toastBinding.root.outlineAmbientShadowColor = ResourcesCompat.getColor(context.resources, R.color.transparent_red, context.theme)
    @RequiresApi(Build.VERSION_CODES.P)
    toastBinding.root.outlineSpotShadowColor = ResourcesCompat.getColor(context.resources, R.color.transparent_red, context.theme)

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

    @RequiresApi(Build.VERSION_CODES.P)
    toastBinding.root.outlineAmbientShadowColor = ResourcesCompat.getColor(context.resources, R.color.transparent_green, context.theme)
    @RequiresApi(Build.VERSION_CODES.P)
    toastBinding.root.outlineSpotShadowColor = ResourcesCompat.getColor(context.resources, R.color.transparent_green, context.theme)

    toast.setGravity(Gravity.BOTTOM, 0, 0)
    toast.view = toastBinding.root
    toast.duration = Toast.LENGTH_SHORT
    toast.show()
}