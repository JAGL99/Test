package com.jagl.pickleapp.core.remote.api

import com.jagl.pickleapp.core.remote.model.GetCharacterById
import com.jagl.pickleapp.core.remote.model.GetEpisodes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character/{ids}")
    suspend fun getCharactersByIds(
        @Path("ids") ids: String
    ): Response<List<GetCharacterById.CharacterRemote>>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: String
    ): Response<GetCharacterById.CharacterRemote>

    @GET("episode/")
    suspend fun getEpisodes(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("episode") episode: String? = null
    ): Response<GetEpisodes.Response>

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): Response<GetEpisodes.RemoteEpisode>
}
