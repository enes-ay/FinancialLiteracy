package com.example.financialliteracy.ui.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.financialliteracy.R

sealed class Screen(val route: String, @DrawableRes val iconId: Int, val label :String) {
    object Profile : Screen("profile", R.drawable.user, "Profile")
    object Home : Screen("home", R.drawable.home, "Home")
    object List : Screen("list", R.drawable.list, "List")
}