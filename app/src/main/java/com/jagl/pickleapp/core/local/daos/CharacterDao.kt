package com.jagl.pickleapp.core.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jagl.pickleapp.core.local.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(media: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacter(mediaList: List<CharacterEntity>)

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterById(id: Long): CharacterEntity?

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Delete
    suspend fun deleteMedia(media: CharacterEntity)

    @Query("DELETE FROM characters")
    suspend fun deleteAllMedia()
}