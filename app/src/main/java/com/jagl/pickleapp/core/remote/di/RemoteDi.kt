package com.jagl.pickleapp.core.remote.di

import com.jagl.pickleapp.core.remote.api.RickAndMortyApi
import com.jagl.pickleapp.core.remote.source.character.CharacterRemoteDataSource
import com.jagl.pickleapp.core.remote.source.character.CharacterRemoteDataSourceImpl
import com.jagl.pickleapp.core.remote.source.episode.EpisodesRemoteDataSource
import com.jagl.pickleapp.core.remote.source.episode.EpisodesRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://rickandmortyapi.com/api/"

@Module
@InstallIn(SingletonComponent::class)
object RemoteDi {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwZjY0MDY3Y2I2MWE1MTBjNzRhMzY1Yjk3N2U5OWExYSIsIm5iZiI6MTYyOTg1MzY4OS44NDgsInN1YiI6IjYxMjU5N2Y5ZDcwNTk0MDA5NTdiZmM4MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.SsN2ehpDN1csxepOZG4lYAT8ewBxgsV46jMkTmxDs64"
                    )
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): RickAndMortyApi {
        return retrofit.create(RickAndMortyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCharacterDataSource(api: RickAndMortyApi): CharacterRemoteDataSource {
        return CharacterRemoteDataSourceImpl(api)
    }

    @Singleton
    @Provides
    fun provideEpisodeDataSource(api: RickAndMortyApi): EpisodesRemoteDataSource {
        return EpisodesRemoteDataSourceImpl(api)
    }


}