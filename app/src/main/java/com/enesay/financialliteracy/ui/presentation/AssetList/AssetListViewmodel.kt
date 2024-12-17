package com.enesay.financialliteracy.ui.presentation.AssetList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesay.financialliteracy.data.repository.CryptoRepository
import com.enesay.financialliteracy.data.repository.StockRepository
import com.enesay.financialliteracy.model.StockModels.Stock
import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.model.Trade.toAsset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetListViewmodel @Inject constructor(
    private val repository: CryptoRepository,
    private val stockRepository: StockRepository,
) : ViewModel() {

    private val _stocks = MutableStateFlow<List<Stock>>(emptyList())
    val stocks: StateFlow<List<Stock>> = _stocks

    private val _cryptoList = MutableStateFlow<List<Asset>>(emptyList())
    val cryptoList: StateFlow<List<Asset>> = _cryptoList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentStart = 1
    private val limit = 6

    // Yeni veriyi yüklemek için trigger
    fun getCryptoData() {
        if (_isLoading.value) return // Çift yükleme önlenir

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newCryptos = repository.getLatestCryptos(currentStart, limit).map {
                    it.toAsset()
                }
                val updatedList = _cryptoList.value + newCryptos
                _cryptoList.emit(updatedList) // Yeni listeyi yayımla
                currentStart += limit
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

//    fun getCryptoData() {
//        viewModelScope.launch {
//            repository.getLatestCryptos()
//                .collect { cryptoList ->
//                    // Map DataCrypto to Asset and update the assets StateFlow
//                    _cryptoData.value = cryptoList.map { it.toAsset() }
//                }
//        }
//    }

    fun getStockQuotes(symbols: String) {
        viewModelScope.launch {
            val quotes = stockRepository.getStockQuotes(symbols)
            _stocks.value = quotes
        }
    }
}
