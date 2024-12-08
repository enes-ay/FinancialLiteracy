package com.enesay.financialliteracy.ui.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enesay.financialliteracy.data.repository.StockRepository
import com.enesay.financialliteracy.model.Stock.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class StockDetailViewmodel @Inject constructor(val stockRepo: StockRepository): ViewModel() {
    val singleStock = MutableLiveData<ApiResponse>()

    fun getStock(symbols:String){
        CoroutineScope(Dispatchers.Main).launch {
            singleStock.value = stockRepo.getStocks(symbols)
        }
    }
}