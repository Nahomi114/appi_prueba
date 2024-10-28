package com.example.appi.api

import com.example.appi.model.NutritionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CalorieNinjasApi {
    @GET("v1/nutrition")
    suspend fun getNutrition(
        @Query("query") query: String,
        @Header("X-Api-Key") apiKey: String = "10DjYY6QAZIq3dpRtWoWwLDhKNEFJE1ds1J11wHv"
    ): Response<NutritionResponse>
}
