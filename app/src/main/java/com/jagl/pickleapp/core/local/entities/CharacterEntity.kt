package com.jagl.pickleapp.core.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jagl.pickleapp.domain.model.CharacterDomain

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val episodes: List<String>,
    @ColumnInfo(name = "origin_name") val originName: String,
    @ColumnInfo(name = "location_name") val locationName: String,
) {
    companion object {
        fun fromDomain(domainModel: CharacterDomain): CharacterEntity {
            return CharacterEntity(
                id = domainModel.id,
                name = domainModel.name,
                status = domainModel.status,
                species = domainModel.species,
                image = domainModel.image,
                originName = domainModel.origin,
                locationName = domainModel.location,
                episodes = domainModel.episodes
            )
        }
    }

    fun toDomain(): CharacterDomain {
        return CharacterDomain(
            id = id,
            name = name,
            status = status,
            species = species,
            image = image,
            origin = originName,
            location = locationName,
            episodes = episodes
        )
    }
}