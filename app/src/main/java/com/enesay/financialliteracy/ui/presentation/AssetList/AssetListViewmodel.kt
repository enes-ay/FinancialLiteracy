package com.enesay.financialliteracy.ui.presentation.AssetList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesay.financialliteracy.data.repository.CryptoRepository
import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.model.Trade.toAsset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetListViewmodel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    private val _cryptoData = MutableStateFlow<List<Asset>>(emptyList())
    val cryptoData: StateFlow<List<Asset>> = _cryptoData

    init {
        getCryptoData()
    }


    fun getCryptoData() {
        viewModelScope.launch {
            repository.getLatestCryptos()
                .collect { cryptoList ->
                    // Map DataCrypto to Asset and update the assets StateFlow
                    _cryptoData.value = cryptoList.map { it.toAsset() }
                }
        }
    }
}
