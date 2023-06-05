package com.prueba.rickandmorty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prueba.rickandmorty.R
import com.prueba.rickandmorty.model.DataDto
import com.prueba.rickandmorty.model.Results

class CharacterAdapter(val context: Context, val layout: Int): RecyclerView.Adapter<CharacterAdapter.ViewHolder>(){

    private var dataDto: DataDto = DataDto()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rowcharacter, parent, false)
        return ViewHolder(view,context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = dataDto.results[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int {
        return dataDto.results.size
    }

    internal fun setCharacters(characters: ArrayList<Results>) {
        this.dataDto.results = characters
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        private val nameCharacter: TextView = itemView.findViewById(R.id.nameTextView)
        private val statusCharacter: TextView = itemView.findViewById(R.id.statusTextView)
        private val speciesCharacter: TextView = itemView.findViewById(R.id.speciesTextView)
        private val ivCharacter : ImageView = itemView.findViewById(R.id.iv_character)

        fun bind(character: Results) {
            Glide.with(this.context).load(character.image).into(ivCharacter)
            nameCharacter.text = character.name
            statusCharacter.text = character.status
            speciesCharacter.text = character.species
        }
    }
}