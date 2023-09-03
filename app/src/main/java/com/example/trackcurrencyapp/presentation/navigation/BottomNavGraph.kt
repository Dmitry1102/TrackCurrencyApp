package com.example.trackcurrencyapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trackcurrencyapp.presentation.screens.favourite.FavouriteScreen
import com.example.trackcurrencyapp.presentation.screens.popular.PopularScreen
import com.example.trackcurrencyapp.presentation.screens.sort.SortScreen

@Composable
fun BottomNavGraph(navController: NavHostController, ) {
   NavHost(
      navController = navController,
      startDestination = BottomBarScreen.Popular.route
   ) {
      composable(route = BottomBarScreen.Popular.route) {
         PopularScreen(navController)
      }

      composable(route = BottomBarScreen.Favourite.route ) {
         FavouriteScreen(navController)
      }

      composable(route = NavigateScreen.Sort.route) {
         SortScreen()
      }
   }

}

