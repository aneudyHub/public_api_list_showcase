package com.example.public_apis_list_showcase.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class Navigator @Inject constructor() {
    private val _sharedFlow = MutableSharedFlow<Routes>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        navigateTo(Routes.HomeRoute)
    }

    fun navigateTo(routes: Routes) {
        _sharedFlow.tryEmit(routes)
    }
}


const val ENTRY_LINK_PARAM = "entry_link_param"