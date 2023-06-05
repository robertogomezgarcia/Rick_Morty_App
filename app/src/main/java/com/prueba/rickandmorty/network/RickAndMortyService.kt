package com.prueba.rickandmorty.network

import com.prueba.rickandmorty.model.DataDto
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    fun getCharacters(@Query("page") page: Int): Deferred<Response<DataDto>>

}