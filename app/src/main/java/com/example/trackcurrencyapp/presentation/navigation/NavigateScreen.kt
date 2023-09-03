package com.example.trackcurrencyapp.presentation.navigation

sealed class NavigateScreen(val route: String) {
   object Sort: NavigateScreen("sort_screen")
}
