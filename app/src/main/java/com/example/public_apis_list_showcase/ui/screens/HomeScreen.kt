package com.example.public_apis_list_showcase.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.public_apis_list_showcase.R
import com.example.public_apis_list_showcase.data.remote.models.Entry
import com.example.public_apis_list_showcase.presentation.HomeScreenViewModel
import com.example.public_apis_list_showcase.ui.views.WebViewContainer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {
    BackHandler(true) {}

    val configuration = LocalConfiguration.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val isTablet = configuration.screenWidthDp >= 600.dp.value


    val uiState = viewModel.uiState.collectAsState()

    if (uiState.value.errorResourceId != null) {
        val errorMessage = stringResource(id = uiState.value.errorResourceId!!)
        LaunchedEffect("home_snackbar_scope") {
            snackbarHostState.showSnackbar(errorMessage)
        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        OutlinedTextField(
            value = uiState.value.filterText,
            singleLine = true,
            onValueChange = { viewModel.onFilterTextChanged(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            label = { Text(text = stringResource(id = R.string.filter)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        )

        if (uiState.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp)
                )
            }
        } else {
            Text(
                text = stringResource(
                    id = R.string.items_found_count,
                    uiState.value.apisList.size
                )
            )
            LazyColumn {
                items(uiState.value.apisList) {
                    Item(entry = it, isTablet = isTablet, onClick = {
                        viewModel.itemHasBeenClicked(it)
                    })
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun Item(entry: Entry, isTablet: Boolean, onClick: () -> Unit) {
    val cardHeight = if (isTablet) 300.dp else 100.dp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(top = 8.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row {
            Column(modifier = Modifier.weight(if (isTablet) 0.5f else 1f)) {
                Text(text = entry.api)
                Text(text = entry.description)
            }
            if (isTablet) {
                Column(modifier = Modifier.weight(0.5f)) {
                    WebViewContainer(url = entry.link)
                }
            }

        }

    }
}


@Preview
@Composable
fun preview() {
    HomeScreen()
}