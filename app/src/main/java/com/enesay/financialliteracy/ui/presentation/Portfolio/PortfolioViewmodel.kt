package com.enesay.financialliteracy.ui.presentation.Portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Asset(val id: Int, val name: String, val value: Double, val price : Int = 23, val symbol: String = "TSLA")

class PortfolioViewmodel : ViewModel() {
    private val _assets = MutableStateFlow<List<Asset>>(emptyList())
    val assets: StateFlow<List<Asset>> = _assets

    init {
        loadAssets()
    }

    private fun loadAssets() {
        // Simulate data loading
        viewModelScope.launch {
            val assetList = listOf(
                Asset(1, "TSLA", 150000.0),
                Asset(2, "BTC", 20000.0),
                Asset(3, "XAU", 1000.0)
            )
            _assets.value = assetList
        }
    }
}