package com.enesay.financialliteracy.ui.presentation
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.enesay.financialliteracy.R

    sealed class Screens(val route: String, @DrawableRes val iconId: Int,@StringRes val label : Int) {
    object Profile : Screens("profile",  R.drawable.ic_person, R.string.item_profile)
    object Home : Screens("home",  R.drawable.ic_home, R.string.item_home)
    object List : Screens("stockList", R.drawable.ic_market, R.string.item_markets)
    object Portolio : Screens("portfolio", R.drawable.ic_wallet, R.string.item_portfolio)
}