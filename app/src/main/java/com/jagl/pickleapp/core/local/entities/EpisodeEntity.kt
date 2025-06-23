package com.jagl.pickleapp.core.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jagl.pickleapp.domain.model.EpisodeDomain

@Entity(tableName = "episodes")
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    @ColumnInfo(name = "air_date")
    val airDate: String,
    val episode: String,
    @ColumnInfo(name = "characters_in_episode")
    val charactersInEpisode: List<Long>,
    val url: String,
    val created: String,
    val page: Int,
) {
    companion object {
        fun fromDomain(domainModel: EpisodeDomain): EpisodeEntity {
            return EpisodeEntity(
                id = domainModel.id,
                name = domainModel.name,
                airDate = domainModel.airDate,
                episode = domainModel.episode,
                charactersInEpisode = domainModel.charactersInEpisode,
                url = domainModel.url,
                created = domainModel.created,
                page = domainModel.page
            )
        }
    }

    fun toDomain(): EpisodeDomain {
        return EpisodeDomain(
            id = this.id,
            name = this.name,
            airDate = this.airDate,
            episode = this.episode,
            charactersInEpisode = this.charactersInEpisode,
            url = this.url,
            created = this.created,
            page = this.page
        )
    }
}