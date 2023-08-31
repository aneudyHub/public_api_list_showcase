package com.example.public_apis_list_showcase.ui.navigation


sealed class Routes(
    open val route: String
) {
    object EmptyRoute : Routes("")
    object HomeRoute : Routes("home")
}

