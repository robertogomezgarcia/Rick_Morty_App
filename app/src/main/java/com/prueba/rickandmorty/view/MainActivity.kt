package com.prueba.rickandmorty.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prueba.rickandmorty.R
import com.prueba.rickandmorty.adapter.CharacterAdapter
import com.prueba.rickandmorty.model.Info
import com.prueba.rickandmorty.model.Results
import com.prueba.rickandmorty.viewmodel.CharacterViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var previousPageButton: Button
    private lateinit var nextPageButton: Button
    private lateinit var currentPageButton: TextView
    private lateinit var edit_search: EditText
    private lateinit var recyclerView: RecyclerView
    private var characterAdapter: CharacterAdapter? = null
    private lateinit var viewmodel: CharacterViewModel
    private var characters: List<Results>? = null
    private var info: Info? = null
    private var currentPage = 1

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val textoFiltro = s.toString().toLowerCase()
            val listFiltered = characters?.filter{ textoFiltro?.let { it1 -> it.name?.toLowerCase()?.contains(it1.toLowerCase()) } ?:false}

            if (listFiltered != null) {
                characterAdapter?.setCharacters(listFiltered as ArrayList<Results>)
            }
            characterAdapter?.notifyDataSetChanged()
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.characterRecyclerView)
        previousPageButton = findViewById(R.id.previousPageButton)
        nextPageButton = findViewById(R.id.nextPageButton)
        currentPageButton = findViewById(R.id.currentPageButton)
        edit_search = findViewById(R.id.edit_search)
        viewmodel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        getCharacters(1)
        initRecyclerView()
        edit_search.addTextChangedListener(textWatcher)

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
                if (edit_search.text.isNotBlank()){
                    val textSearch = edit_search.text.toString()
                    val listFiltered = characters?.filter{ textSearch?.let { it1 -> it.name?.toLowerCase()?.contains(it1.toString().toLowerCase()) } ?:false}
                    characterAdapter?.setCharacters(listFiltered as ArrayList<Results>)
                }else{
                    showCharacters(characters as ArrayList<Results>)
                }
            }
        }
    }

    private fun getInfo(info: Info?) {
        val  nextPage = info?.next?.let { getPreviousNextPages(it) } ?: info?.pages.toString()
        val previousPage = info?.prev?.let { getPreviousNextPages(it) } ?: currentPage
        currentPageButton.text = String.format("Pag %s",currentPage.toString())
        nextPageButton.text = nextPage
        previousPageButton.text = previousPage.toString()
        previousPageButton.isEnabled = previousPage != currentPage
        nextPageButton.isEnabled = nextPage != info?.pages.toString()
    }

    private fun showCharacters(characters: ArrayList<Results>) {
        characterAdapter?.setCharacters(characters)
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
