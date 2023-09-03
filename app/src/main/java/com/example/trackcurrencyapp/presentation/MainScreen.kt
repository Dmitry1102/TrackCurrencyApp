package com.example.trackcurrencyapp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.trackcurrencyapp.R
import com.example.trackcurrencyapp.presentation.navigation.BottomBarScreen
import com.example.trackcurrencyapp.presentation.navigation.BottomNavGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
   val navController: NavHostController = rememberNavController()
   Scaffold(
      bottomBar = { BottomBar(navController = navController) }
   ) {
      BottomNavGraph(navController = navController)
   }
}

@Composable
fun BottomBar(
   navController: NavHostController
) {
   val screens = listOf(
      BottomBarScreen.Popular,
      BottomBarScreen.Favourite
   )

   val navBackStackEntry by navController.currentBackStackEntryAsState()
   val currentDestination = navBackStackEntry?.destination
   
   BottomNavigation(
      modifier = Modifier.height(height = 70.dp),
      backgroundColor = colorResource(id = R.color.black),
      contentColor = Color.White,
   ){
      screens.forEach{ screen ->
         AddItem(
            screen = screen,
            currentDestination = currentDestination,
            navController = navController
         )
      }
   }
}

@Composable
fun RowScope.AddItem(
   screen: BottomBarScreen,
   currentDestination: NavDestination?,
   navController: NavHostController
) {
   BottomNavigationItem(
      label = {
         Text(
            text = screen.title,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp

         )
      },
      icon = {
         Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
         ) {
            Icon(
               imageVector = screen.icon,
               modifier = Modifier.size(24.dp),
               contentDescription = "Navigation Icon"
            )
            Spacer(
               modifier = Modifier.height(10.dp)
            )
         }
      },
      selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
      onClick = {
         navController.navigate(screen.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
         }
      })
}