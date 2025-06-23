package com.jagl.pickleapp.domain.model

data class CharacterDomain(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: String,
    val location: String,
    val episodes: List<String>,
)