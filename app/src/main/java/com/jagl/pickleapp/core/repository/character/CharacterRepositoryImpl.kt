package com.jagl.pickleapp.core.repository.character

import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.core.local.source.character.CharacterLocalDataSource
import com.jagl.pickleapp.core.remote.model.GetCharacterById
import com.jagl.pickleapp.core.remote.source.character.CharacterRemoteDataSource
import com.jagl.pickleapp.domain.model.CharacterDomain
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource,
    private val dispatcherProvider: DispatcherProvider
) : CharacterRepository {

    override suspend fun getCharactersByIds(ids: List<Long>): List<CharacterDomain> =
        runBlocking(dispatcherProvider.io) {
            val localData = ids.map { id -> localDataSource.getById(id) }
            if (localData.isNotEmpty() && localData.all { it != null }) {
                return@runBlocking localData as List<CharacterDomain>
            }

            val data = remoteDataSource.getCharactersByIds(ids)
            if (data.isFailure) {
                return@runBlocking emptyList()
            }

            val remoteData = data
                .getOrElse { emptyList() }
                .map(GetCharacterById.CharacterRemote::toDomain)
            localDataSource.insertAll(remoteData)

            return@runBlocking remoteData
        }

    override suspend fun getCharacterById(id: Long): CharacterDomain? =
        runBlocking(dispatcherProvider.io) {
            val localData = localDataSource.getById(id)
            if (localData != null) {
                return@runBlocking localData
            }

            val data = remoteDataSource.getCharacterById(id)
            if (data.isFailure) {
                return@runBlocking null
            }

            val remoteData = data
                .getOrNull()?.toDomain()
                ?.also { domain -> localDataSource.insert(domain) }

            return@runBlocking remoteData
        }
}