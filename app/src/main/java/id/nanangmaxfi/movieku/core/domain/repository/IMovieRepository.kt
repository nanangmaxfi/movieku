package id.nanangmaxfi.movieku.core.domain.repository

import androidx.lifecycle.LiveData
import id.nanangmaxfi.movieku.core.data.source.Resource
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: MovieDetail, state: Boolean)
    fun getDetailMovie(movieId: Int): Flow<Resource<MovieDetail>>
}