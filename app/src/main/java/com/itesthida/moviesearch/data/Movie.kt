package com.itesthida.moviesearch.data

import com.google.gson.annotations.SerializedName

data class Movie(
    val imdbID: String,
    val Title: String,
    val Year: String,
    val Poster: String,
    val Plot: String?,
    val Runtime: String?,
    val Director: String?,
    val Genre: String?,
    val Country: String?

) {
}
data class MovieList(
    @SerializedName("Search") val results : List<Movie>
)