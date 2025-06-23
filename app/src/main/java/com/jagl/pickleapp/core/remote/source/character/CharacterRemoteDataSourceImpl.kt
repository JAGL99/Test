package com.jagl.pickleapp.core.remote.source.character

import com.jagl.pickleapp.core.remote.api.RickAndMortyApi
import com.jagl.pickleapp.core.remote.model.GetCharacterById
import com.jagl.pickleapp.core.remote.utils.RequestUtils.safeCall
import javax.inject.Inject


class CharacterRemoteDataSourceImpl @Inject constructor(
    private val api: RickAndMortyApi
) : CharacterRemoteDataSource {

    override suspend fun getCharactersByIds(ids: List<Long>): Result<List<GetCharacterById.CharacterRemote>> {
        return safeCall {
            val request = api.getCharactersByIds(ids = ids.toString())
            if (request.isSuccessful && request.body() != null) {
                Result.success(request.body()!!)
            } else {
                Result.failure(
                    Exception("Failed to fetch episode by ID: ${request.errorBody()?.string()}")
                )
            }
        }

    }

    override suspend fun getCharacterById(id: Long): Result<GetCharacterById.CharacterRemote> {
        return safeCall {
            val request = api.getCharacterById(id = id.toString())
            if (request.isSuccessful && request.body() != null) {
                Result.success(request.body()!!)
            } else {
                Result.failure(
                    Exception("Failed to fetch character by ID: ${request.errorBody()?.string()}")
                )
            }

        }
    }
}