package id.nanangmaxfi.movieku.core.data.source

import androidx.lifecycle.LiveData
import id.nanangmaxfi.movieku.core.data.source.local.LocalDataSource
import id.nanangmaxfi.movieku.core.data.source.remote.RemoteDataSource
import id.nanangmaxfi.movieku.core.data.source.remote.network.ApiResponse
import id.nanangmaxfi.movieku.core.data.source.remote.response.ListMovieResponse
import id.nanangmaxfi.movieku.core.data.source.remote.response.ResultsItem
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.core.domain.repository.IMovieRepository
import id.nanangmaxfi.movieku.core.utils.AppExecutors

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository{

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllMovie(): LiveData<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, ListMovieResponse>(appExecutors){
            override fun loadFromDB(): LiveData<List<Movie>> {
                TODO("Not yet implemented")
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                TODO("Not yet implemented")
            }

            override fun createCall(): LiveData<ApiResponse<ListMovieResponse>> {
                TODO("Not yet implemented")
            }

            override fun saveCallResult(data: ListMovieResponse) {
                TODO("Not yet implemented")
            }

        }.asLiveData()

    override fun getFavoriteMovie(): LiveData<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        TODO("Not yet implemented")
    }
}