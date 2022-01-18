package id.nanangmaxfi.movieku.core.domain.usecase

import id.nanangmaxfi.movieku.core.data.source.Resource
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.core.domain.model.MovieDetail
import id.nanangmaxfi.movieku.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> = movieRepository.getAllMovie()

    override fun getFavoriteMovie(): Flow<List<Movie>> =movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: MovieDetail, state: Boolean) = movieRepository.setFavoriteMovie(movie, state)

    override fun getDetailMovie(movieId: Int): Flow<Resource<MovieDetail>> = movieRepository.getDetailMovie(movieId)
}