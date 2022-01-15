package id.nanangmaxfi.movieku.ui.favorite

import androidx.lifecycle.ViewModel
import id.nanangmaxfi.movieku.core.domain.usecase.MovieUseCase

class FavoriteMovieViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movieListFavorite = movieUseCase.getFavoriteMovie()
}