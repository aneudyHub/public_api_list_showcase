package com.example.public_apis_list_showcase.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.public_apis_list_showcase.presentation.DetailsScreenViewModel
import com.example.public_apis_list_showcase.ui.views.WebViewContainer


@Composable
fun DetailsScreen(entryLink: String?, viewModel: DetailsScreenViewModel = hiltViewModel()) {
    BackHandler(true) {
        viewModel.onBackPressed()
    }

    if (entryLink.isNullOrBlank()) {
        Box(modifier = Modifier.fillMaxSize()) {}
    } else {
        Surface(modifier = Modifier.fillMaxSize()) {
            WebViewContainer(url = entryLink)
        }
    }
}