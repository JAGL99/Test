package com.jagl.pickleapp.core.repository.character

import com.jagl.pickleapp.domain.model.CharacterDomain

interface CharacterRepository {
    suspend fun getCharactersByIds(ids: List<Long>): List<CharacterDomain>
    suspend fun getCharacterById(id: Long): CharacterDomain?
}