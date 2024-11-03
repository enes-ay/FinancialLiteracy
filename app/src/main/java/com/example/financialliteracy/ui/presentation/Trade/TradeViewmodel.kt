package com.example.financialliteracy.ui.presentation.Trade

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialliteracy.data.repository.CryptoRepository
import com.example.financialliteracy.data.repository.TradeRepository
import com.example.financialliteracy.model.DataCrypto
import com.example.financialliteracy.model.Trade.Asset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeViewmodel @Inject constructor(val tradeRepository: TradeRepository) : ViewModel()  {
    private val _assets = MutableLiveData<List<Asset>>()
    val assets: LiveData<List<Asset>> = _assets

    fun loadAssets() {
        viewModelScope.launch {
            _assets.value = tradeRepository.getAssets()
        }
    }

    fun buyAsset(asset: Asset, quantity: Double) {
        viewModelScope.launch {
            tradeRepository.buyAsset(asset, quantity)
            loadAssets()
        }
    }

    fun sellAsset(asset: Asset, quantity: Double) {
        viewModelScope.launch {
            tradeRepository.sellAsset(asset, quantity)
            loadAssets()
        }
    }
}