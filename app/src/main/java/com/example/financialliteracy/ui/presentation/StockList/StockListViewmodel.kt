package com.example.financialliteracy.ui.presentation.StockList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financialliteracy.data.repository.StockRepository
import com.example.financialliteracy.model.ApiResponse
import com.example.financialliteracy.model.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockListViewmodel @Inject constructor(val stockRepo: StockRepository): ViewModel() {

    val stockList = MutableLiveData<ApiResponse>()

    fun getStocks(symbols:String){
        CoroutineScope(Dispatchers.Main).launch {
            stockList.value = stockRepo.getStocks(symbols)
        }
    }
}