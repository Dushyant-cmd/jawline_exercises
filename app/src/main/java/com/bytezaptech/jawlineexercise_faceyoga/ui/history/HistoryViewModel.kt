package com.bytezaptech.jawlineexercise_faceyoga.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.GrowthEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(val mainRepo: MainRepository): ViewModel() {
    val historyLD: LiveData<Response>
        get() {
            return mainRepo.historyLD
        }
    val articleLD: LiveData<Response>
        get() {
            return mainRepo.articleLD
        }

    fun getHistory() {
        mainRepo.getHistory()
    }

    fun getArticles() {
        viewModelScope.launch(Dispatchers.Main){
            mainRepo.getArticles()
        }
    }
}

class HistoryViewModelFactory(val mainRepo: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryViewModel(mainRepo) as T
    }

}