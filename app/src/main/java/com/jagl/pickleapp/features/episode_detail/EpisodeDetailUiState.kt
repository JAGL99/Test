package com.jagl.pickleapp.features.episode_detail

import com.jagl.pickleapp.domain.model.CharacterDomain
import com.jagl.pickleapp.domain.model.EpisodeDomain

data class EpisodeDetailUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: EpisodeDomain? = null,
    val charactersInEpisode: List<CharacterDomain> = emptyList(),
)