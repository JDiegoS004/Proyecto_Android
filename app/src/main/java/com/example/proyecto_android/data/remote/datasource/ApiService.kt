package com.example.proyecto_android.data.remote.datasource

import com.example.proyecto_android.data.remote.model.MovieApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService
{
    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): MovieApiResponse
}
