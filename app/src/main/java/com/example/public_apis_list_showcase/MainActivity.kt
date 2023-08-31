package com.example.public_apis_list_showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.public_apis_list_showcase.ui.navigation.Navigation
import com.example.public_apis_list_showcase.ui.navigation.Navigator
import com.example.public_apis_list_showcase.ui.theme.Public_apis_list_showcaseTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Public_apis_list_showcaseTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Navigation(navController, navigator)
            }
        }
    }
}