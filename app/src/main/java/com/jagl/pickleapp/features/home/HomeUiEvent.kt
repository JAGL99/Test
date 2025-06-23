package com.jagl.pickleapp.features.home

sealed class HomeUiEvent {
    data class GoToEpisodeDetails(val id: Long) : HomeUiEvent()
    data object GetMoreEpisodes : HomeUiEvent()
    data class SearchQueryChanged(val query: String) : HomeUiEvent()
    data object Search : HomeUiEvent()
    data object Logout : HomeUiEvent()
    data object ClearSearch : HomeUiEvent()
    data class OnSearchTypeChanged(val type: String) : HomeUiEvent()
}