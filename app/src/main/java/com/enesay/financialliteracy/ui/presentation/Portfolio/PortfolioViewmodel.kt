package com.enesay.financialliteracy.ui.presentation.Portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesay.financialliteracy.data.repository.WalletRepository
import com.enesay.financialliteracy.model.Trade.Asset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewmodel @Inject constructor(val repository: WalletRepository) : ViewModel() {
    private val _assets = MutableStateFlow<List<Asset>>(emptyList())
    val assets: StateFlow<List<Asset>> = _assets

    private val _userAssets = MutableStateFlow<List<Asset>>(emptyList())
    val userAssets: StateFlow<List<Asset>> = _userAssets.asStateFlow()

    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    private var _userId = MutableStateFlow("")
    var userId: StateFlow<String> = _userId

    fun getUserAssets(userId: String) {
        viewModelScope.launch {
            if (userId != null) {
                val assets = repository.getUserAssets(userId)
                _userAssets.value = assets
            }
        }
    }

     fun getBalance(userId: String) {
        viewModelScope.launch {
            val balance = repository.getUserBalance(userId)
            _balance.value = balance
        }
    }
}