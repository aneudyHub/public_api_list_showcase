package com.example.public_apis_list_showcase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.public_apis_list_showcase.ui.screens.HomeScreen


@Composable
fun Navigation(navController: NavHostController, navigator: Navigator) {
    LaunchedEffect("navigator") {
        navigator.sharedFlow.collect {
            navController.navigate(it.route)
        }
    }

    NavHost(navController = navController, startDestination = Routes.HomeRoute.route) {
        composable(Routes.HomeRoute.route) {
            HomeScreen()
        }
    }
}