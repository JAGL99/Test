package com.jagl.pickleapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.pickleapp.core.repository.episode.EpisodeRepository
import com.jagl.pickleapp.domain.usecase.logout.Logout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository,
    private val logout: Logout
) : ViewModel() {

    private var job: Job? = null

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getFirstPageOfEpisodes()
    }

    private fun getFirstPageOfEpisodes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val page = 1
            try {
                val data = episodeRepository.getEpisodesByPage(page)
                _uiState.update {
                    it.copy(
                        data = data,
                        isLoading = false,
                        errorMessage = null,
                        page = page
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "Error fetching episodes")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Unknown error",
                        data = emptyList()
                    )
                }
            }


        }

    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.GetMoreEpisodes -> getMoreEpisodes()
            is HomeUiEvent.GoToEpisodeDetails -> onGoToDetail(event.id)
            HomeUiEvent.ClearSearch -> onClearSearch()
            HomeUiEvent.Search -> onSearch()
            is HomeUiEvent.SearchQueryChanged -> onSearchQueryChanged(event.query)
            is HomeUiEvent.OnSearchTypeChanged -> onSearchTypeChanged(event.type)
            HomeUiEvent.Logout -> onLogout()
        }
    }

    private fun onLogout() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        logout(
            onSuccess = { emitEvent(HomeUiEvent.Logout) },
            onError = { message ->
                _uiState.update { it.copy(errorMessage = message) }
            }
        )
        _uiState.update { it.copy(isLoading = false) }
    }


    private fun onSearchTypeChanged(searchType: String) {
        _uiState.update { it.copy(searchType = searchType) }
        if (_uiState.value.query.isNotEmpty()) {
            onEvent(HomeUiEvent.Search)
        }
    }

    private fun onSearch() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        val query = _uiState.value.query.lowercase()
        val searchType = _uiState.value.searchType
        val result = episodeRepository.getEpisodesByQuery(query, searchType)
        _uiState.update { it.copy(isLoading = false, data = result) }
    }

    private fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
        job?.cancel()
        job = viewModelScope.launch {
            delay(1000)
            onEvent(HomeUiEvent.Search)
        }
    }

    private fun onClearSearch() {
        _uiState.update { it.copy(query = "", data = emptyList()) }
        job?.cancel()
        job = null
        getFirstPageOfEpisodes()
    }

    private fun getMoreEpisodes() {
        viewModelScope.launch {
            val nextPage = _uiState.value.page + 1
            val currenteData = _uiState.value.data
            val result = episodeRepository.getEpisodesByPage(nextPage)
            if (result.isEmpty()) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Unkow error",
                        isLoading = false
                    )
                }
            } else {
                _uiState.update { it.copy(page = nextPage, data = currenteData + result) }
            }

        }
    }

    private fun onGoToDetail(id: Long) {
        _uiState.update { it.copy(isLoading = true) }
        emitEvent(
            HomeUiEvent.GoToEpisodeDetails(id)
        )
        _uiState.update { it.copy(isLoading = false) }
    }

    private fun emitEvent(event: HomeUiEvent) = viewModelScope.launch {
        _uiEvent.emit(event)
    }
}