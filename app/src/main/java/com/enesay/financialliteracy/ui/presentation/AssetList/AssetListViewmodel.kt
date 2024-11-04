package com.enesay.financialliteracy.ui.presentation.AssetList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesay.financialliteracy.data.repository.CryptoRepository
import com.enesay.financialliteracy.model.DataCrypto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetListViewmodel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    private val _cryptoData = MutableStateFlow<List<DataCrypto>>(emptyList())
    val cryptoData: StateFlow<List<DataCrypto>> = _cryptoData

    init {
        getCryptoData()
    }

    fun getCryptoData() {
        viewModelScope.launch {
            repository.getLatestCryptos()
                .collect { cryptoList ->
                    _cryptoData.value = cryptoList
                    Log.d("gelendata", "${cryptoList.get(0).name}")
                }
        }
    }
}
