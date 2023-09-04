package com.example.public_apis_list_showcase.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.public_apis_list_showcase.data.remote.models.Entry
import com.example.public_apis_list_showcase.domain.GetApisListUseCase
import com.example.public_apis_list_showcase.domain.models.UseCaseResult
import com.example.public_apis_list_showcase.presentation.extensions.getResourceId
import com.example.public_apis_list_showcase.ui.navigation.ENTRY_LINK_PARAM
import com.example.public_apis_list_showcase.ui.navigation.Navigator
import com.example.public_apis_list_showcase.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getApisListUseCase: GetApisListUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var job = Job()

    init {
        getApisList()
    }

    internal fun getApisList(filterText: String = "") {
        job.cancel()
        job = Job()
        _uiState.update { it.copy(isLoading = true, errorResourceId = null) }
        CoroutineScope(viewModelScope.coroutineContext + job).launch {
            when (val apisList = getApisListUseCase(filterText)) {
                is UseCaseResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, apisList = apisList.data) }
                }

                is UseCaseResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorResourceId = apisList.errorType.getResourceId()
                        )
                    }
                }
            }
        }
    }

    fun itemHasBeenClicked(entry: Entry) {
        val route = Routes.DetailsRoute
        val map = hashMapOf<String, Any>()
        map[ENTRY_LINK_PARAM] = entry.link
        route.params = map
        navigator.navigateTo(route)
    }

    fun onFilterTextChanged(filterText: String) {
        _uiState.update { it.copy(filterText = filterText) }
        getApisList(filterText)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    data class UiState(
        val isLoading: Boolean = false,
        val apisList: List<Entry> = emptyList(),
        val filterText: String = "",
        val errorResourceId: Int? = null
    )
}