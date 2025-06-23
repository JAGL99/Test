package com.jagl.pickleapp.core.repository.di

import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.core.local.source.character.CharacterLocalDataSource
import com.jagl.pickleapp.core.local.source.episode.EpisodeLocalDataSource
import com.jagl.pickleapp.core.remote.source.character.CharacterRemoteDataSource
import com.jagl.pickleapp.core.remote.source.episode.EpisodesRemoteDataSource
import com.jagl.pickleapp.core.repository.character.CharacterRepository
import com.jagl.pickleapp.core.repository.character.CharacterRepositoryImpl
import com.jagl.pickleapp.core.repository.episode.EpisodeRepository
import com.jagl.pickleapp.core.repository.episode.EpisodeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryDi {

    @Singleton
    @Provides
    fun provideCharacterRepository(
        remoteDataSource: CharacterRemoteDataSource,
        localDataSource: CharacterLocalDataSource,
        dispatcherProvider: DispatcherProvider
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            dispatcherProvider = dispatcherProvider
        )
    }

    @Singleton
    @Provides
    fun provideEpisodeRepository(
        remoteDataSource: EpisodesRemoteDataSource,
        localDataSource: EpisodeLocalDataSource,
        dispatcherProvider: DispatcherProvider
    ): EpisodeRepository {
        return EpisodeRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            dispatcherProvider = dispatcherProvider
        )
    }
}