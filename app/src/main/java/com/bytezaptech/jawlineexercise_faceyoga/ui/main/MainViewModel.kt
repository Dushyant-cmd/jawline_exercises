package com.bytezaptech.jawlineexercise_faceyoga.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModel(val context: Context): ViewModel() {
    var isDataReady: Boolean = true
}

class MainViewModelFactory(val context: Context): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}