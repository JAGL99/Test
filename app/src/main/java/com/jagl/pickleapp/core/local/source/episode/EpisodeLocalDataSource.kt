package com.jagl.pickleapp.core.local.source.episode

import com.jagl.pickleapp.domain.model.EpisodeDomain
import kotlinx.coroutines.flow.Flow

interface EpisodeLocalDataSource {
    fun getById(id: Long): Flow<EpisodeDomain?>
    fun getByPage(page: Int): Flow<List<EpisodeDomain>>
    fun getByName(name: String): Flow<List<EpisodeDomain>>
    fun getByEpisode(episode: String): Flow<List<EpisodeDomain>>
    suspend fun insertAll(domain: List<EpisodeDomain>)
    suspend fun insert(domain: EpisodeDomain)
    suspend fun deleteAll()
    suspend fun delete(domain: EpisodeDomain)
}