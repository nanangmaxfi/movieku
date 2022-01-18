package id.nanangmaxfi.movieku.core.domain.model

/**
 * Created by nanangmaxfi on 09/01/22.
 */


data class Movie(
    val movieId : Int,
    val posterPath: String,
    val title: String,
    val releaseDate: String,
    val rating: Double,
    val isFavorite: Boolean
)
