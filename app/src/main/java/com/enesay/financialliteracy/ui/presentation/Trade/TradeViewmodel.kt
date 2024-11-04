package com.enesay.financialliteracy.ui.presentation.Trade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesay.financialliteracy.common.Resource
import com.enesay.financialliteracy.data.repository.AuthRepository
import com.enesay.financialliteracy.data.repository.TradeRepository
import com.enesay.financialliteracy.data.repository.WalletRepository
import com.enesay.financialliteracy.model.Trade.Asset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeViewmodel @Inject constructor(
    val authRepository: AuthRepository,
    val tradeRepository: TradeRepository, val walletRepository: WalletRepository) : ViewModel()  {
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _balance = MutableLiveData<Double>()
    val balance: LiveData<Double> get() = _balance

    private val _assets = MutableLiveData<Map<String, Double>>()
    val assets: LiveData<Map<String, Double>> get() = _assets

    fun loadUserData() {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            _balance.value = walletRepository.getUserBalance(userId)
            _assets.value = walletRepository.getUserAssets(userId)
        }
    }

    fun buyAsset(asset: Asset, amount: Double) {
        viewModelScope.launch {
            val result = tradeRepository.buyAsset(asset, amount)
            if (result is Resource.Error) {
                _errorMessage.value = result.exception.toString()
            } else {
                loadUserData()
            }
        }
    }

    fun sellAsset(asset: Asset, amount: Double) {
        viewModelScope.launch {
            val result = tradeRepository.sellAsset(asset, amount)
            if (result is Resource.Error) {
                _errorMessage.value = result.exception.message
            } else {
                loadUserData()
            }
        }
    }
}