@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.public_apis_list_showcase.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(title: String) {
    Column {
        TopAppBar(
            title = { Text(text = title) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
                titleContentColor = MaterialTheme.colorScheme.primary
            )
        )

    }
}