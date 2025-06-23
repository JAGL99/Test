package com.jagl.pickleapp.core.local.source.episode

import com.jagl.pickleapp.core.local.daos.EpisodeDao
import com.jagl.pickleapp.core.local.entities.EpisodeEntity
import com.jagl.pickleapp.domain.model.EpisodeDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EpisodeLocalDataSourceImpl @Inject constructor(
    private val episodeDao: EpisodeDao
) : EpisodeLocalDataSource {

    override fun getById(id: Long): Flow<EpisodeDomain?> {
        return episodeDao.getById(id).map { entity -> entity?.toDomain() }
    }


    override fun getByPage(page: Int): Flow<List<EpisodeDomain>> {
        return episodeDao.getByPage(page).map { it.map(EpisodeEntity::toDomain) }
    }

    override fun getByName(name: String): Flow<List<EpisodeDomain>> {
        return episodeDao.getByName(name).map { it.map(EpisodeEntity::toDomain) }
    }

    override fun getByEpisode(episode: String): Flow<List<EpisodeDomain>> {
        return episodeDao.getByEpisode(episode).map { it.map(EpisodeEntity::toDomain) }
    }

    override suspend fun insertAll(domain: List<EpisodeDomain>) {
        val entities = domain.map { character -> EpisodeEntity.fromDomain(character) }
        episodeDao.insertAll(entities)
    }

    override suspend fun insert(domain: EpisodeDomain) {
        episodeDao.insert(EpisodeEntity.fromDomain(domain))
    }

    override suspend fun deleteAll() {
        episodeDao.deleteAll()
    }

    override suspend fun delete(domain: EpisodeDomain) {
        episodeDao.delete(EpisodeEntity.fromDomain(domain))
    }

}