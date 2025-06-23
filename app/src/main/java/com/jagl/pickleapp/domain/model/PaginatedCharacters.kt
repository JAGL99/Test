package com.jagl.pickleapp.domain.model

data class PaginatedCharacters(
    val info: Info,
    val characters: List<CharacterDomain>
)
