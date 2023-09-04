package com.example.public_apis_list_showcase.presentation

import androidx.lifecycle.ViewModel
import com.example.public_apis_list_showcase.ui.navigation.Navigator
import com.example.public_apis_list_showcase.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onBackPressed() {
        navigator.navigateTo(Routes.HomeRoute)
    }
}