package com.example.public_apis_list_showcase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.public_apis_list_showcase.ui.screens.DetailsScreen
import com.example.public_apis_list_showcase.ui.screens.HomeScreen


@Composable
fun Navigation(navController: NavHostController, navigator: Navigator) {
    LaunchedEffect("navigator") {
        navigator.sharedFlow.collect {
            if (it.params != null) {
                for (param in it.params!!.entries) {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        param.key,
                        param.value
                    )
                }
            }
            navController.navigate(route = it.route)
        }
    }

    NavHost(navController = navController, startDestination = Routes.HomeRoute.route) {
        composable(
            route = Routes.HomeRoute.route
        ) {
            HomeScreen()
        }
        composable(
            route = Routes.DetailsRoute.route
        ) {
            val entryLink =
                navController.previousBackStackEntry?.savedStateHandle?.get<String>(ENTRY_LINK_PARAM)
            DetailsScreen(entryLink)
        }
    }
}