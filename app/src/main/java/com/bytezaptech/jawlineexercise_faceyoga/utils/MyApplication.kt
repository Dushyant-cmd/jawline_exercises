package com.bytezaptech.jawlineexercise_faceyoga.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Application
import android.util.Log
import android.view.View
import com.bytezaptech.jawlineexercise_faceyoga.di.AppComponent
import com.bytezaptech.jawlineexercise_faceyoga.di.DaggerAppComponent
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus


class MyApplication: Application() {
    lateinit var appComponent: AppComponent
    private val TAG = "MyApplication"
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        appComponent = DaggerAppComponent.factory().create(this)

        //To initialize mobile ads sdk which do initial setup or preload ads at app launch
        MobileAds.initialize(this) { initializationStatus: InitializationStatus? ->
            Log.d(TAG, "initialize status: $initializationStatus")
        }
    }

    fun scaleView(view: View, isDown: Boolean) {
        when(isDown) {
            true -> {
                val scaleDownX = ObjectAnimator.ofFloat(
                    view,
                    "scaleX", 0.9f
                )
                val scaleDownY = ObjectAnimator.ofFloat(
                    view,
                    "scaleY", 0.9f
                )
                scaleDownX.setDuration(500)
                scaleDownY.setDuration(500)
                val scaleDown = AnimatorSet()
                scaleDown.play(scaleDownX).with(scaleDownY)
                scaleDown.start()
            }
            else -> {
                val scaleDownX2 = ObjectAnimator.ofFloat(
                    view, "scaleX", 1f
                )
                val scaleDownY2 = ObjectAnimator.ofFloat(
                    view, "scaleY", 1f
                )
                scaleDownX2.setDuration(500)
                scaleDownY2.setDuration(500)
                val scaleDown2 = AnimatorSet()
                scaleDown2.play(scaleDownX2).with(scaleDownY2)
                scaleDown2.start()
            }
        }

    }
}