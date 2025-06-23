package com.jagl.pickleapp.features.character_detail

import com.jagl.pickleapp.domain.model.CharacterDomain

data class CharacterDetailUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: CharacterDomain? = null
)