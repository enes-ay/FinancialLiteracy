package com.enesay.financialliteracy.ui.presentation
import androidx.annotation.DrawableRes
import com.enesay.financialliteracy.R

    sealed class Screens(val route: String, @DrawableRes val iconId: Int, val label :String) {
    object Profile : Screens("profile",  R.drawable.ic_person, "Profile")
    object Home : Screens("home",  R.drawable.ic_home, "Home")
    object List : Screens("stockList", R.drawable.ic_market, "Markets")
    object Portolio : Screens("portfolio", R.drawable.ic_wallet, "Portfolio")
}