package com.prueba.rickandmorty.repositories

import com.prueba.rickandmorty.model.DataDto
import com.prueba.rickandmorty.network.UrlAccess

class CharacterRepository() {
    val service = UrlAccess.rickAndMortyService

    suspend fun getCharacters(page: Int): DataDto{
        val webResponse = service.getCharacters(page).await()

        if (webResponse.isSuccessful){
            return webResponse.body()!!
        }
        return DataDto()
    }
}