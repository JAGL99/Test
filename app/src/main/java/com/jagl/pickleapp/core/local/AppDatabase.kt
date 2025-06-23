package com.jagl.pickleapp.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jagl.pickleapp.core.local.daos.CharacterDao
import com.jagl.pickleapp.core.local.daos.EpisodeDao
import com.jagl.pickleapp.core.local.entities.CharacterEntity
import com.jagl.pickleapp.core.local.entities.EpisodeEntity
import com.jagl.pickleapp.core.local.typeConverters.StringListConverter

@Database(
    entities = [
        CharacterEntity::class,
        EpisodeEntity::class
    ],
    version = 1
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    abstract fun episodeDao(): EpisodeDao

    companion object {
        const val DATABASE_NAME = "pickleapp_database"
    }
}