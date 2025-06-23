package com.jagl.pickleapp.core.local.source.character

import com.jagl.pickleapp.core.local.daos.CharacterDao
import com.jagl.pickleapp.core.local.entities.CharacterEntity
import com.jagl.pickleapp.domain.model.CharacterDomain
import javax.inject.Inject

class CharacterLocalDataSourceImpl @Inject constructor(
    private val characterDao: CharacterDao
) : CharacterLocalDataSource {

    override fun getById(id: Long): CharacterDomain? {
        return characterDao.getCharacterById(id)?.toDomain()
    }

    override suspend fun insertAll(domain: List<CharacterDomain>) {
        val entities = domain.map { character -> CharacterEntity.fromDomain(character) }
        characterDao.insertAllCharacter(entities)
    }

    override suspend fun insert(domain: CharacterDomain) {
        characterDao.insertCharacter(CharacterEntity.fromDomain(domain))
    }

    override suspend fun deleteAll() {
        characterDao.deleteAllMedia()
    }

    override suspend fun delete(domain: CharacterDomain) {
        characterDao.deleteMedia(CharacterEntity.fromDomain(domain))
    }

}