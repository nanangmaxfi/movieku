package id.nanangmaxfi.movieku.core.domain.model

data class MovieDetail(
    val movieId : Int = 0,
    val posterPath: String = "",
    val title: String = "",
    val releaseDate: String = "",
    val rating: Double = 0.0,
    val tagline: String = "",
    val genre: String = "",
    val status: String = "",
    val homepage: String = "",
    val countries: String = "",
    val production: String = "",
    val overview: String = "",
    val isFavorite: Boolean = false
)
