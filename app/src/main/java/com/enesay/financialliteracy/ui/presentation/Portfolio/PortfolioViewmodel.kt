package com.enesay.financialliteracy.ui.presentation.Portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesay.financialliteracy.data.repository.WalletRepository
import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.ui.presentation.AssetList.AssetListViewmodel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewmodel @Inject constructor(
    val repository: WalletRepository,
    val assetListViewModel: AssetListViewmodel) : ViewModel() { /** Need to switch to use case **/

    private val _assets = MutableStateFlow<List<Asset>>(emptyList())
    val assets: StateFlow<List<Asset>> = _assets

    private val _userAssets = MutableStateFlow<List<Asset>>(emptyList())
    val userAssets: StateFlow<List<Asset>> = _userAssets.asStateFlow()

    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    private var _userId = MutableStateFlow("")
    var userId: StateFlow<String> = _userId

    // TODO: Use this function as use case
    fun getUserAssets(userId: String) {
        viewModelScope.launch {
            if (userId != null) {
                val assets = repository.getUserAssets(userId)
                _userAssets.value = assets
                calculateBalance(userId)
            }
        }
    }

    fun calculateBalance(userId: String) {
        viewModelScope.launch {
            val userAssets = repository.getUserAssets(userId) // Kullanıcı varlıkları

            // Güncel fiyatlar
            assetListViewModel.cryptoList.collect { cryptoList ->
                val balance = userAssets.sumOf { asset ->
                    val currentPrice =
                        cryptoList.find { it.symbol == asset.symbol }?.price ?: 0.0
                    asset.quantity * currentPrice
                }
                _balance.value = balance
                _userAssets.value = userAssets.map { asset ->
                    val currentPrice =
                        cryptoList.find { it.symbol == asset.symbol }?.price ?: 0.0
                    asset.copy(price = currentPrice)
                }
                repository.updateUserBalance(userId, balance)
            }
        }
    }
}