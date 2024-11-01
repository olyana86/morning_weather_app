package com.example.morningapp.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("key") apikey: String,
        @Query("q") city: String,
        @Query("lang") language: String
    ): Response<WeatherModel>
}