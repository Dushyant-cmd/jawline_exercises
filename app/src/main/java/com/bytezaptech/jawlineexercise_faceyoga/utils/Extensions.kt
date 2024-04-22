package com.bytezaptech.jawlineexercise_faceyoga.utils

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.viewpager2.widget.ViewPager2

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
        override fun onAnimationStart(animation: Animator, isReverse: Boolean) { beginFakeDrag() }
        override fun onAnimationStart(p0: Animator) {
            //ignore
        }

        override fun onAnimationEnd(animation: Animator, isReverse: Boolean) { endFakeDrag() }
        override fun onAnimationEnd(p0: Animator) {
            //ignore
        }

        override fun onAnimationCancel(p0: Animator) { /* Ignored */ }
        override fun onAnimationRepeat(p0: Animator) { /* Ignored */ }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}