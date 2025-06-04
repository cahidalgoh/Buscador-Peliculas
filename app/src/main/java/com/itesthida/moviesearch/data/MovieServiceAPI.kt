package com.itesthida.moviesearch.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServiceAPI {
    companion object{
        fun getInstance():MovieServiceAPI{
            val retrofit = Retrofit.Builder().baseUrl(
                "https://www.omdbapi.com/"
            ).addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit.create(MovieServiceAPI::class.java)
        }
    }

    @GET("/?apikey=f23771c0")
    suspend fun findMovieByName(@Query("s") title:String):MovieList

    @GET("/?apikey=f23771c0")
    suspend fun findMovieById(@Query("i") title:String):Movie
}