package id.nanangmaxfi.movieku.core.utils

import androidx.lifecycle.MediatorLiveData
import id.nanangmaxfi.movieku.core.data.source.Resource
import id.nanangmaxfi.movieku.core.data.source.local.entity.MovieEntity
import id.nanangmaxfi.movieku.core.data.source.remote.network.ApiResponse
import id.nanangmaxfi.movieku.core.data.source.remote.response.*
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.core.domain.model.MovieDetail

object DataMapper {
    fun mapListResponsesToListEntities(input: ListMovieResponse) : List<MovieEntity>{
        val movieList = ArrayList<MovieEntity>()
        input.results?.map{
            val movie = MovieEntity(
                movieId = it?.id?:0,
                title = it?.title?:"",
                posterPath = it?.posterPath?:"",
                releaseDate = it?.releaseDate?:"",
                voteAverage = it?.voteAverage?:0.0,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapListEntityToListDomain(input : List<MovieEntity>) : List<Movie> =
        input.map {
            Movie(
                movieId = it.movieId,
                posterPath = it.posterPath,
                title = it.title,
                releaseDate = it.releaseDate,
                rating = it.voteAverage,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: MovieDetail) = MovieEntity(
        movieId = input.movieId,
        title = input.title,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        voteAverage = input.rating,
        isFavorite = input.isFavorite
    )

    fun mapDetailResponseToDetailDomain(input: DetailMovieResponse) = MovieDetail(
        movieId = input.id?:0,
        posterPath = input.posterPath?:"",
        title = input.title?:"",
        releaseDate = input.releaseDate?:"",
        rating = input.voteAverage?:0.0,
        countVote = input.voteCount?:0,
        tagline = input.tagline?:"",
        genre = genreListToString(input.genres),
        status = input.status?:"",
        homepage = input.homepage?:"",
        countries = countriesListToString(input.productionCountries),
        production = productionListToString(input.productionCompanies),
        overview = input.overview?:"",
        isFavorite = false
    )

    private fun genreListToString(genres: List<GenresItem?>?) : String{
        if (genres != null) {
            return genres.asSequence()
                .filterNotNull()
                .map(GenresItem::name)
                .joinToString(separator = ", ")
        }
        return ""
    }

    private fun countriesListToString(countries: List<ProductionCountriesItem?>?) : String{
        if (countries != null) {
            return countries.asSequence()
                .filterNotNull()
                .map(ProductionCountriesItem::name)
                .joinToString(separator = ", ")
        }
        return ""
    }

    private fun productionListToString(production: List<ProductionCompaniesItem?>?) : String{
        if (production != null) {
            return production.asSequence()
                .filterNotNull()
                .map(ProductionCompaniesItem::name)
                .joinToString(separator = ", ")
        }
        return ""
    }
}