package id.nanangmaxfi.movieku.core.domain.usecase

import androidx.lifecycle.LiveData
import id.nanangmaxfi.movieku.core.data.source.Resource
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.core.domain.model.MovieDetail

interface MovieUseCase {
    fun getAllMovie(): LiveData<Resource<List<Movie>>>
    fun getFavoriteMovie(): LiveData<List<Movie>>
    fun setFavoriteMovie(movie: MovieDetail, state: Boolean)
    fun getDetailMovie(movieId: Int): LiveData<Resource<MovieDetail>>
}