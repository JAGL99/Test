package com.jagl.pickleapp.core.remote.model

import com.jagl.pickleapp.domain.model.CharacterDomain

object GetCharacterById {

    data class CharacterRemote(
        val created: String?,
        val episode: List<String>?,
        val gender: String?,
        val id: Long?,
        val image: String?,
        val location: ApiLocation?,
        val name: String?,
        val origin: ApiOrigin?,
        val species: String?,
        val status: String?,
        val type: String?,
        val url: String?
    ) {
        data class ApiLocation(
            val name: String?
        )

        data class ApiOrigin(
            val name: String?
        )

        fun toDomain(): CharacterDomain {
            return CharacterDomain(
                id = this.id ?: 0L,
                name = this.name ?: "",
                status = this.status ?: "",
                species = this.species ?: "",
                image = this.image ?: "",
                origin = this.origin?.name ?: "",
                location = this.location?.name ?: "",
                episodes = this.episode ?: emptyList()
            )
        }
    }
}