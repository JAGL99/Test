package com.jagl.pickleapp.core.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jagl.pickleapp.core.local.entities.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: EpisodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<EpisodeEntity>)

    @Query("SELECT * FROM episodes WHERE id = :id")
    fun getById(id: Long): Flow<EpisodeEntity?>

    @Query("SELECT * FROM episodes WHERE page = :page")
    fun getByPage(page: Int): Flow<List<EpisodeEntity>>

    @Query("SELECT * FROM episodes")
    fun getAll(): Flow<List<EpisodeEntity>>

    @Query("SELECT * FROM episodes WHERE name LIKE '%' || :name || '%'")
    fun getByName(name: String): Flow<List<EpisodeEntity>>

    @Query("SELECT * FROM episodes WHERE episode LIKE '%' || :episode || '%'")
    fun getByEpisode(episode: String): Flow<List<EpisodeEntity>>

    @Delete
    suspend fun delete(entity: EpisodeEntity)

    @Query("DELETE FROM episodes")
    suspend fun deleteAll()
}