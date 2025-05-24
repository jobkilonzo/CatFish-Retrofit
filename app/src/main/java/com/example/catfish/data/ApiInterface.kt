package com.example.catfish.data

import com.example.catfish.module.CatFacts
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/fact")
    suspend fun getRandomFacts():Response<CatFacts>
}