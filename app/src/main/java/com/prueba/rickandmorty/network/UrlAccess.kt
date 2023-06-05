package com.prueba.rickandmorty.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UrlAccess {

    val rickAndMortyService : RickAndMortyService by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("https://rickandmortyapi.com/api/")
            .build()

        return@lazy retrofit.create(RickAndMortyService::class.java)
    }
}