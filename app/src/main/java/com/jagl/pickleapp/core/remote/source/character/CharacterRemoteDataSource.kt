package com.jagl.pickleapp.core.remote.source.character

import com.jagl.pickleapp.core.remote.model.GetCharacterById

interface CharacterRemoteDataSource {
    suspend fun getCharactersByIds(ids: List<Long>): Result<List<GetCharacterById.CharacterRemote>>
    suspend fun getCharacterById(id: Long): Result<GetCharacterById.CharacterRemote>
}