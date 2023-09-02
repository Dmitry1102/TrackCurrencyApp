package com.example.trackcurrencyapp.navigation

sealed class NavigateScreen(val route: String) {
   object Sort: NavigateScreen("sort_screen")
}
