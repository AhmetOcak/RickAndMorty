package com.rickandmorty.domain.model.location

data class Results(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: ArrayList<String>,
    val url: String,
    val createdDate: String
)
