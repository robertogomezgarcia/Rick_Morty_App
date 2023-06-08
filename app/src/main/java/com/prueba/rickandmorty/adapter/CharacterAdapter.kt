package com.prueba.rickandmorty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prueba.rickandmorty.R
import com.prueba.rickandmorty.model.DataDto
import com.prueba.rickandmorty.model.Results

class CharacterAdapter(val context: Context, val layout: Int): RecyclerView.Adapter<CharacterAdapter.ViewHolder>(){
    private var isFirstLayoutVisible = true
    private var isSecondLayoutVisible = false
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
        private val speciesCharacter: TextView = itemView.findViewById(R.id.tv_specie_value)
        private val genderCharacter: TextView = itemView.findViewById(R.id.tv_gender_value)
        private val statusCharacter: TextView = itemView.findViewById(R.id.tv_status_value)

        private val originCharacter: TextView = itemView.findViewById(R.id.tv_origin_value)
        private val locationCharacter: TextView = itemView.findViewById(R.id.tv_location_value)
        private val episodesCharacter: TextView = itemView.findViewById(R.id.tv_episodes_value)

        private val ivCharacter : ImageView = itemView.findViewById(R.id.iv_character)
        private val characterConstraint : ConstraintLayout = itemView.findViewById(R.id.character_constraint)
        private val characterDetailConstraint : ConstraintLayout = itemView.findViewById(R.id.detail_character_constraint)

        fun bind(character: Results) {
            Glide.with(this.context).load(character.image).into(ivCharacter)
            nameCharacter.text = character.name
            speciesCharacter.text = character.species
            genderCharacter.text = character.gender
            statusCharacter.text = character.status
            statusCharacter.setTextColor(
                when(character.status){
                    "Alive" -> getColor(this.context, R.color.green_jungle)
                    "unknown" -> getColor(this.context, R.color.orange)
                    else -> getColor(this.context, R.color.red)
                }
            )


            originCharacter.text = character.origin?.name
            locationCharacter.text = character.location?.name
            episodesCharacter.text = character.episode.count().toString()

            itemView.setOnClickListener {
                    flip()
            }
        }

        private fun flip() {
            if (characterConstraint.isVisible){
                characterConstraint.animate()
                    .rotationY(90f)
                    .duration = 1000
                characterDetailConstraint.animate()
                    .rotationY(0f)
                    .duration = 1000
                characterConstraint.visibility = View.GONE
                characterDetailConstraint.visibility = View.VISIBLE
            }else{
                characterConstraint.animate()
                    .rotationY(0f)
                    .duration = 1000
                characterDetailConstraint.animate()
                    .rotationY(90f)
                    .duration = 1000
                characterConstraint.visibility = View.VISIBLE
                characterDetailConstraint.visibility = View.GONE
            }
        }
    }
}