package com.example.proyecto_android.data.remote.model

data class MovieResult(
val id: Int,
val title: String,
val poster_path: String?,
val release_date: String?,
val overview: String
)