package id.nanangmaxfi.movieku.core.data.source

import id.nanangmaxfi.movieku.core.data.source.local.LocalDataSource
import id.nanangmaxfi.movieku.core.data.source.remote.RemoteDataSource
import id.nanangmaxfi.movieku.core.data.source.remote.network.ApiResponse
import id.nanangmaxfi.movieku.core.data.source.remote.response.ListMovieResponse
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.core.domain.model.MovieDetail
import id.nanangmaxfi.movieku.core.domain.repository.IMovieRepository
import id.nanangmaxfi.movieku.core.utils.AppExecutors
import id.nanangmaxfi.movieku.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, ListMovieResponse>(appExecutors){
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map{
                    DataMapper.mapListEntityToListDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()


            override suspend fun createCall(): Flow<ApiResponse<ListMovieResponse>> =
                remoteDataSource.getListTrendingMovie()


            override suspend fun saveCallResult(data: ListMovieResponse) {
                val movieList = DataMapper.mapListResponsesToListEntities(data)
                return localDataSource.insertMovie(movieList)
            }

        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map{
            DataMapper.mapListEntityToListDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: MovieDetail, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute{ localDataSource.setFavoriteMovie(movieEntity, state)}
    }

    override fun getDetailMovie(movieId: Int): Flow<Resource<MovieDetail>> {
        return remoteDataSource.getDetailMovie(movieId.toString()).map{ response ->
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