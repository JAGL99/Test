package com.jagl.pickleapp.features.home

import com.jagl.pickleapp.core.utils.extensions.SEARCH_BY_NAME
import com.jagl.pickleapp.domain.model.EpisodeDomain

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val page: Int = 0,
    val data: List<EpisodeDomain> = emptyList(),
    val query: String = "",
    val searchType: String = String.SEARCH_BY_NAME
)