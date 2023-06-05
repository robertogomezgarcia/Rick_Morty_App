package com.prueba.rickandmorty.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prueba.rickandmorty.R
import com.prueba.rickandmorty.adapter.CharacterAdapter
import com.prueba.rickandmorty.model.Info
import com.prueba.rickandmorty.model.Results
import com.prueba.rickandmorty.network.RickAndMortyService
import com.prueba.rickandmorty.viewmodel.CharacterViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var previousPageButton: Button
    private lateinit var nextPageButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var rickAndMortyService: RickAndMortyService
    private lateinit var viewmodel: CharacterViewModel
    private lateinit var characters: List<Results>
    private var info: Info? = null
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.characterRecyclerView)
        previousPageButton = findViewById(R.id.previousPageButton)
        nextPageButton = findViewById(R.id.nextPageButton)
        viewmodel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        getCharacters(1)
        initRecyclerView()

        previousPageButton.setOnClickListener{
            if (currentPage > 1) {
                currentPage --
                getCharacters(currentPage)
            }
        }

        nextPageButton.setOnClickListener{
            if (currentPage < info?.pages!!){
                currentPage++
                getCharacters(currentPage)
            }
        }
    }

    private fun initRecyclerView() {
        characterAdapter = CharacterAdapter(this, R.layout.rowcharacter)
        recyclerView.adapter = characterAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getCharacters(page: Int) {
        viewmodel.getCharacters(page).observe(this){
            it ->
            it?.let {
                info = it.info
                getInfo(info)
                characters = it.results
                showCharacters(characters as ArrayList<Results>)
            }
        }
    }

    private fun getInfo(info: Info?) {
        val  nextPage = info?.next?.let { getPreviousNextPages(it) } ?: info?.pages.toString()
        val previousPage = info?.prev?.let { getPreviousNextPages(it) } ?: currentPage
        nextPageButton.text = nextPage
        previousPageButton.text = previousPage.toString()
        previousPageButton.isEnabled = previousPage != currentPage
        nextPageButton.isEnabled = nextPage != info?.pages.toString()
    }

    private fun showCharacters(characters: ArrayList<Results>) {
        characterAdapter.setCharacters(characters)
    }

    fun getPreviousNextPages(url: String): String? {
        val parametro = "page="
        val indiceInicio = url.indexOf(parametro)
        if (indiceInicio != -1) {
            return url.substring(indiceInicio + parametro.length)
        }
        return null
    }
}