package com.enesay.financialliteracy.ui.presentation.Trade

sealed class TradeState {
    object Loading : TradeState()
    object Success : TradeState()
    data class Warning(val message: String) : TradeState()
    data class Error(val message: String) : TradeState()
}