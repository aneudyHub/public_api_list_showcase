package com.example.public_apis_list_showcase.ui.navigation

import com.example.public_apis_list_showcase.R

sealed class Routes(
    open val route: String,
    open val screenNameResource: Int = 0,
    open var params: HashMap<String, Any>? = null
) {
    object HomeRoute : Routes(
        route = "home_screen",
        screenNameResource = R.string.home_screen_name
    )

    object DetailsRoute : Routes(
        route = "details_screen",
        screenNameResource = R.string.details_screen_name
    )
}

