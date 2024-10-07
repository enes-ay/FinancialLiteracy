package com.example.financialliteracy.ui.presentation.StockList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financialliteracy.data.repository.StockRepository
import com.example.financialliteracy.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockListViewmodel @Inject constructor(val stockRepo: StockRepository) : ViewModel() {
    val searchList = MutableLiveData<List<Result>>()

    fun searchStock(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            searchList.value = stockRepo.searchStock(query).result
        }
    }
}