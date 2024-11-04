package com.enesay.financialliteracy.ui.presentation
import androidx.annotation.DrawableRes
import com.enesay.financialliteracy.R

    sealed class Screen(val route: String,@DrawableRes val iconId: Int, val label :String) {
    object Profile : Screen("profile",  R.drawable.ic_person, "Profile")
    object Home : Screen("home",  R.drawable.ic_home, "Home")
    object List : Screen("stockList", R.drawable.ic_menu, "List")
    object Portolio : Screen("portfolio", R.drawable.ic_wallet, "Portfolio")
}