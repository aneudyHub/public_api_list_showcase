package com.example.public_apis_list_showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.public_apis_list_showcase.ui.navigation.Navigation
import com.example.public_apis_list_showcase.ui.navigation.Navigator
import com.example.public_apis_list_showcase.ui.navigation.Routes
import com.example.public_apis_list_showcase.ui.theme.Public_apis_list_showcaseTheme
import com.example.public_apis_list_showcase.ui.views.AppToolbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Public_apis_list_showcaseTheme {
                val navController = rememberNavController()
                val currentScreenName =
                    navigator.sharedFlow.collectAsState(initial = Routes.HomeRoute)

                Scaffold(
                    topBar = {
                        AppToolbar(title = stringResource(id = currentScreenName.value.screenNameResource))
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        Navigation(navController, navigator)
                    }
                }
            }
        }
    }
}