package id.nanangmaxfi.movieku.di

import id.nanangmaxfi.movieku.core.domain.usecase.MovieInteractor
import id.nanangmaxfi.movieku.core.domain.usecase.MovieUseCase
import id.nanangmaxfi.movieku.ui.detail.DetailMovieViewModel
import id.nanangmaxfi.movieku.ui.favorite.FavoriteMovieViewModel
import id.nanangmaxfi.movieku.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
}
