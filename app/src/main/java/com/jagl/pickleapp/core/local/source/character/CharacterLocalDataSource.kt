package com.jagl.pickleapp.core.local.source.character

import com.jagl.pickleapp.domain.model.CharacterDomain

interface CharacterLocalDataSource {
    fun getById(id: Long): CharacterDomain?
    suspend fun insertAll(domain: List<CharacterDomain>)
    suspend fun insert(domain: CharacterDomain)
    suspend fun deleteAll()
    suspend fun delete(domain: CharacterDomain)
}