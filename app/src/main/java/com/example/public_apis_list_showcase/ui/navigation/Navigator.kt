package com.example.public_apis_list_showcase.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class Navigator @Inject constructor() {
    private val _sharedFlow = MutableSharedFlow<Routes>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun navigateTo(routes: Routes) {
        _sharedFlow.tryEmit(routes)
    }
}