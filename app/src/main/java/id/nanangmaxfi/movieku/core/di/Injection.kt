package id.nanangmaxfi.movieku.core.di

import android.content.Context
import id.nanangmaxfi.movieku.core.data.source.MovieRepository
import id.nanangmaxfi.movieku.core.data.source.local.LocalDataSource
import id.nanangmaxfi.movieku.core.data.source.local.room.MovieDatabase
import id.nanangmaxfi.movieku.core.data.source.remote.RemoteDataSource
import id.nanangmaxfi.movieku.core.domain.repository.IMovieRepository
import id.nanangmaxfi.movieku.core.domain.usecase.MovieInteractor
import id.nanangmaxfi.movieku.core.domain.usecase.MovieUseCase
import id.nanangmaxfi.movieku.core.utils.AppExecutors

object Injection {
    private fun provideRepository(context: Context): IMovieRepository {
        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideTourismUseCase(context: Context): MovieUseCase {
        val repository = provideRepository(context)
        return MovieInteractor(repository)
    }
}