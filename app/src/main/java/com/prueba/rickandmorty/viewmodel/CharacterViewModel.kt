package com.prueba.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.rickandmorty.model.DataDto
import com.prueba.rickandmorty.repositories.CharacterRepository
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CharacterViewModel: ViewModel() {
    private var repository: CharacterRepository = CharacterRepository()

    fun getCharacters(page: Int): MutableLiveData<DataDto>{
        val data = MutableLiveData<DataDto>()
        GlobalScope.launch(Main){
            data.value = repository.getCharacters(page)
        }
        return data
    }
}