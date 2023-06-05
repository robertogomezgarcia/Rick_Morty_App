package com.prueba.rickandmorty.model

import com.google.gson.annotations.SerializedName

data class DataDto(
    @SerializedName("info"    ) var info    : Info?              = Info(),
    @SerializedName("results" ) var results : ArrayList<Results> = arrayListOf()
)
