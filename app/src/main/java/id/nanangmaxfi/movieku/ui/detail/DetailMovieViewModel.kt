package id.nanangmaxfi.movieku.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.nanangmaxfi.movieku.core.domain.model.MovieDetail
import id.nanangmaxfi.movieku.core.domain.usecase.MovieUseCase

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun movieDetail(movieId: Int) = movieUseCase.getDetailMovie(movieId).asLiveData()
    fun setFavoriteMovie(movieDetail: MovieDetail, status: Boolean) =
        movieUseCase.setFavoriteMovie(movieDetail, status)
}