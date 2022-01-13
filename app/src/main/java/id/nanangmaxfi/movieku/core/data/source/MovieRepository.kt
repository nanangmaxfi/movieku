package id.nanangmaxfi.movieku.core.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import id.nanangmaxfi.movieku.core.data.source.local.LocalDataSource
import id.nanangmaxfi.movieku.core.data.source.remote.RemoteDataSource
import id.nanangmaxfi.movieku.core.data.source.remote.network.ApiResponse
import id.nanangmaxfi.movieku.core.data.source.remote.response.ListMovieResponse
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.core.domain.model.MovieDetail
import id.nanangmaxfi.movieku.core.domain.repository.IMovieRepository
import id.nanangmaxfi.movieku.core.utils.AppExecutors
import id.nanangmaxfi.movieku.core.utils.DataMapper

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
                return Transformations.map(localDataSource.getAllMovie()){
                    DataMapper.mapListEntityToListDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()


            override fun createCall(): LiveData<ApiResponse<ListMovieResponse>> =
                remoteDataSource.getListTrendingMovie()


            override fun saveCallResult(data: ListMovieResponse) {
                val movieList = DataMapper.mapListResponsesToListEntities(data)
                return localDataSource.insertMovie(movieList)
            }

        }.asLiveData()

    override fun getFavoriteMovie(): LiveData<List<Movie>> {
        return Transformations.map(localDataSource.getFavoriteMovie()){
            DataMapper.mapListEntityToListDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: MovieDetail, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute{ localDataSource.setFavoriteMovie(movieEntity, state)}
    }

    override fun getDetailMovie(movieId: Int): LiveData<Resource<MovieDetail>> {
        return Transformations.map(remoteDataSource.getDetailMovie(movieId.toString())){ response ->
            when (response) {
                is ApiResponse.Success ->
                    Resource.Success(DataMapper.mapDetailResponseToDetailDomain(response.data))
                is ApiResponse.Empty ->
                    Resource.Success(MovieDetail())
                is ApiResponse.Error -> {
                    Resource.Error(response.errorMessage, MovieDetail())
                }
            }
        }
    }
}