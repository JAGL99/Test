package com.jagl.pickleapp.core.local.di

import android.content.Context
import androidx.room.Room
import com.jagl.pickleapp.core.local.AppDatabase
import com.jagl.pickleapp.core.local.daos.CharacterDao
import com.jagl.pickleapp.core.local.daos.EpisodeDao
import com.jagl.pickleapp.core.local.source.character.CharacterLocalDataSource
import com.jagl.pickleapp.core.local.source.character.CharacterLocalDataSourceImpl
import com.jagl.pickleapp.core.local.source.episode.EpisodeLocalDataSource
import com.jagl.pickleapp.core.local.source.episode.EpisodeLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseDi {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideCharacterDao(
        appDatabase: AppDatabase
    ): CharacterDao {
        return appDatabase.characterDao()
    }

    @Singleton
    @Provides
    fun provideEpisodeDao(
        appDatabase: AppDatabase
    ): EpisodeDao {
        return appDatabase.episodeDao()
    }

    @Singleton
    @Provides
    fun provideEpisodeLocalDataSource(
        episodeDao: EpisodeDao
    ): EpisodeLocalDataSource {
        return EpisodeLocalDataSourceImpl(episodeDao)
    }

    @Singleton
    @Provides
    fun provideCharacterLocalDataSource(
        characterDao: CharacterDao
    ): CharacterLocalDataSource {
        return CharacterLocalDataSourceImpl(characterDao)
    }

}