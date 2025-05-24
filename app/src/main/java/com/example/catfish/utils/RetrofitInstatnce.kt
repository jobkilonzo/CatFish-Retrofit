package com.example.catfish.utils

import com.example.catfish.data.ApiInterface
import retrofit2.Retrofit

object RetrofitInstatnce {
    ///fact
    val api: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.Base)
            .addConverterFactory()
    }
}