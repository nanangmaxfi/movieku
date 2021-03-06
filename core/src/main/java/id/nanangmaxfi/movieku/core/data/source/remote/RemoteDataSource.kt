package id.nanangmaxfi.movieku.core.data.source.remote

import android.util.Log
import id.nanangmaxfi.movieku.core.BuildConfig
import id.nanangmaxfi.movieku.core.data.source.remote.network.ApiResponse
import id.nanangmaxfi.movieku.core.data.source.remote.network.ApiService
import id.nanangmaxfi.movieku.core.data.source.remote.response.DetailMovieResponse
import id.nanangmaxfi.movieku.core.data.source.remote.response.ListMovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {

    fun getListTrendingMovie() : Flow<ApiResponse<ListMovieResponse>>{
        return flow {
            try {
                val response = apiService.getListTrendingMovie(BuildConfig.API_KEY)
                emit(ApiResponse.Success(response))
            }
            catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailMovie(idMovie: String) : Flow<ApiResponse<DetailMovieResponse>>{

        return flow {
            try {
                val response = apiService.getDetailMovie(idMovie, BuildConfig.API_KEY)
                emit(ApiResponse.Success(response))
            }
            catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}