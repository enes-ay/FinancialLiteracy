package com.enesay.financialliteracy.ui.presentation.AssetList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesay.financialliteracy.data.repository.CryptoRepository
import com.enesay.financialliteracy.data.repository.StockRepository
import com.enesay.financialliteracy.model.Stock.search.StockResponse
import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.model.Trade.StockAsset
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

    private val _cryptoList = MutableStateFlow<List<Asset>>(emptyList())
    val cryptoList: StateFlow<List<Asset>> = _cryptoList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentStart = 1
    private val limit = 6

    private val _stockResponse = MutableStateFlow<StockResponse?>(null)
    val stockResponse: StateFlow<StockResponse?> = _stockResponse

    private val _stockSearchList = MutableStateFlow<List<StockAsset>>(listOf())
    val stockSearchList: StateFlow<List<StockAsset>> = _stockSearchList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

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

    fun fetchStockData(symbol: String) {
        viewModelScope.launch {
            try {
                val response = stockRepository.getStockQuotes(symbol)
                if (response.isSuccessful) {
                    Log.d("asset", "success ${response.body().toString()}")
                    // API çağrısı başarılıysa veriyi al
                    _stockResponse.value = response.body()
                    _errorMessage.value = null // Hata mesajını temizle
                } else {
                    // API çağrısı başarısızsa hata kodunu kontrol et
                    val errorMessage = when (response.code()) {
                        400 -> "Geçersiz istek. Lütfen girişlerinizi kontrol edin."
                        401 -> "Yetkisiz erişim. API anahtarınızı kontrol edin."
                        404 -> "Veri bulunamadı. Hatalı bir sembol girilmiş olabilir."
                        else -> "Beklenmeyen bir hata oluştu: ${response.message()}"
                    }
                    Log.d("asset", "else  ${response.body().toString()}")

                    _errorMessage.value = errorMessage
                }
            } catch (e: Exception) {
                // Ağ veya diğer beklenmeyen hataları ele al
                Log.d("asset", "catch ${e.message}")

                _errorMessage.value = "Bir hata oluştu: ${e.localizedMessage ?: "Bilinmeyen hata"}"
            }
        }
    }

    fun searchStockSymbol(symbol: String) {
        viewModelScope.launch {
            try {
                val response = stockRepository.searchStockSymbol(symbol)
                if (response.isSuccessful) {
                    _stockSearchList.value = response.body()?.result?.map {
                        it.toAsset()
                    } ?: listOf()
                    Log.d("veriler", "geliyor ${response.body().toString()}")
                    _errorMessage.value = null
                }
                else{
                    _errorMessage.value = response.message() ?: "Unknown error"
                }

            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
