package com.example.financialliteracy.ui.presentation.Portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Asset(val id: Int, val name: String, val value: Double)

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
                Asset(1, "House", 150000.0),
                Asset(2, "Car", 20000.0),
                Asset(3, "Laptop", 1000.0)
            )
            _assets.value = assetList
        }
    }
}