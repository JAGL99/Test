package com.jagl.pickleapp.core.remote.source.episode

import com.jagl.pickleapp.core.remote.api.RickAndMortyApi
import com.jagl.pickleapp.core.remote.model.GetEpisodes
import com.jagl.pickleapp.core.remote.utils.RequestUtils.safeCall
import javax.inject.Inject


class EpisodesRemoteDataSourceImpl @Inject constructor(
    private val api: RickAndMortyApi
) : EpisodesRemoteDataSource {

    override suspend fun getEpisodesByPage(page: Int): Result<GetEpisodes.Response> {
        return safeCall {
            val request = api.getEpisodes(page = page)
            if (request.isSuccessful && request.body() != null) {
                Result.success(request.body()!!)
            } else {
                Result.failure(
                    Exception(
                        "Failed to fetch episodes: ${
                            request.errorBody()?.string()
                        }"
                    )
                )
            }
        }
    }

    override suspend fun getEpisodesByName(query: String): Result<List<GetEpisodes.RemoteEpisode>> {
        return safeCall {
            val request = api.getEpisodes(name = query)
            if (request.isSuccessful && request.body()?.episodes != null) {
                Result.success(request.body()!!.episodes!!)
            } else {
                Result.failure(
                    Exception(
                        "Failed to fetch episodes by name: ${request.errorBody()?.string()}"
                    )
                )
            }
        }
    }

    override suspend fun getEpisodesByEpisode(query: String): Result<List<GetEpisodes.RemoteEpisode>> {
        return safeCall {
            val request = api.getEpisodes(episode = query)
            if (request.isSuccessful && request.body()?.episodes != null) {
                Result.success(request.body()!!.episodes!!)
            } else {
                Result.failure(
                    Exception(
                        "Failed to fetch episodes by episode: ${
                            request.errorBody()?.string()
                        }"
                    )
                )
            }
        }
    }

    override suspend fun getEpisodeById(id: Int): Result<GetEpisodes.RemoteEpisode> {
        return safeCall {
            val request = api.getEpisodeById(id)
            if (request.isSuccessful && request.body() != null) {
                Result.success(request.body()!!)
            } else {
                Result.failure(
                    Exception(
                        "Failed to fetch episode by ID: ${
                            request.errorBody()?.string()
                        }"
                    )
                )
            }
        }
    }
}