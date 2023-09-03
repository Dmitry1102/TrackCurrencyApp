package com.example.trackcurrencyapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Popular : BottomBarScreen(
            route = "popular_screen",
            title = "Popular",
            icon = Icons.Default.ShoppingCart
    )

    object Favourite : BottomBarScreen(
            route = "favourite_screen",
            title = "Favourite",
            icon = Icons.Default.Favorite
    )
}

