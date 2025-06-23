package com.jagl.pickleapp.core.repository.episode

import com.jagl.pickleapp.domain.model.EpisodeDomain

interface EpisodeRepository {
    suspend fun getEpisodesByPage(page: Int): List<EpisodeDomain>

    suspend fun getEpisodesByQuery(
        query: String,
        type: String
    ): List<EpisodeDomain>

    suspend fun getEpisodeById(id: Long): EpisodeDomain
}