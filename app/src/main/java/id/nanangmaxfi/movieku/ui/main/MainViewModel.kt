package id.nanangmaxfi.movieku.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.nanangmaxfi.movieku.core.domain.usecase.MovieUseCase

class MainViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movieList = movieUseCase.getAllMovie().asLiveData()
}