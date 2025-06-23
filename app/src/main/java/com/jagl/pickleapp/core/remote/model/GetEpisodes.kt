package com.jagl.pickleapp.core.remote.model

import com.google.gson.annotations.SerializedName
import com.jagl.pickleapp.core.utils.extensions.DEFAULT_PAGE_VALUE
import com.jagl.pickleapp.domain.model.EpisodeDomain

object GetEpisodes {

    data class Response(
        val info: ApiInfo?,
        @SerializedName("results")
        val episodes: List<RemoteEpisode>?
    )

    data class RemoteEpisode(
        @SerializedName("air_date")
        val airDate: String,
        val characters: List<String>,
        val created: String,
        val episode: String,
        val id: Long,
        val name: String,
        val url: String
    ) {
        fun toDomain(): EpisodeDomain {
            return EpisodeDomain(
                id = this.id,
                name = this.name,
                airDate = this.airDate,
                episode = this.episode,
                charactersInEpisode = this.characters.mapNotNull { characterUrl ->
                    characterUrl.substringAfterLast("/").toLongOrNull()
                },
                url = this.url,
                created = this.created,
                page = Int.DEFAULT_PAGE_VALUE
            )
        }
    }
}