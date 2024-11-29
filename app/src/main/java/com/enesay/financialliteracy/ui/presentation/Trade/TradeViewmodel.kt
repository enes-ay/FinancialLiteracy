package com.enesay.financialliteracy.ui.presentation.Trade

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeViewmodel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tradeRepository: TradeRepository,
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _balance = MutableLiveData<Double>()
    val balance: LiveData<Double> get() = _balance

    private val _userAssets = MutableStateFlow<List<Asset>>(emptyList())
    val userAssets: StateFlow<List<Asset>> = _userAssets.asStateFlow()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _transactionSuccess = MutableLiveData<Boolean>()
    val transactionSuccess: LiveData<Boolean> get() = _transactionSuccess

    fun loadUserData() {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _balance.value = walletRepository.getUserBalance(userId)
                _userAssets.value = walletRepository.getUserAssets(userId)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun buyAsset(asset: Asset, purchasePrice: Double, quantity: Double) {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val purchaseAmount = purchasePrice * quantity
                walletRepository.addUserAsset(
                    userId = userId,
                    asset = asset.copy(quantity = quantity),
                    purchasePrice = purchasePrice,
                    purchaseAmount = purchaseAmount,
                    purchaseDate = System.currentTimeMillis().toString()
                )
                walletRepository.updateUserBalance(userId, (_balance.value ?: 0.0) - purchaseAmount)
                _transactionSuccess.value = true
                loadUserData()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sellAsset(asset: Asset, quantity: Double, sellPrice: Double) {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userAssets = walletRepository.getUserAssets(userId)
                val userAsset = userAssets.find { it.symbol == asset.symbol }

                // Validate if the user has enough quantity to sell
                if (userAsset == null || userAsset.quantity < quantity) {
                    _errorMessage.value = "Insufficient ${asset.symbol} to sell"
                    return@launch
                }

                // Calculate sale amount
                val saleAmount = quantity * asset.price

                // Update Firestore
                walletRepository.sellUserAsset(userId, asset.symbol, quantity, sellPrice)
                walletRepository.updateUserBalance(userId, (_balance.value ?: 0.0) + saleAmount)

                // Update local state
                _transactionSuccess.value = true
                loadUserData()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}

