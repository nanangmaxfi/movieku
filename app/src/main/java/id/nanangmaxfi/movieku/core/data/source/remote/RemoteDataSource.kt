package id.nanangmaxfi.movieku.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.nanangmaxfi.movieku.BuildConfig
import id.nanangmaxfi.movieku.core.data.source.remote.network.ApiConfig
import id.nanangmaxfi.movieku.core.data.source.remote.network.ApiResponse
import id.nanangmaxfi.movieku.core.data.source.remote.response.DetailMovieResponse
import id.nanangmaxfi.movieku.core.data.source.remote.response.ListMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getListTrendingMovie() : LiveData<ApiResponse<ListMovieResponse?>>{
        val result = MutableLiveData<ApiResponse<ListMovieResponse?>>()

        ApiConfig.getApiService().getListTrendingMovie(BuildConfig.API_KEY).enqueue(object :
            Callback<ListMovieResponse>{
            override fun onResponse(
                call: Call<ListMovieResponse>,
                response: Response<ListMovieResponse>
            ) {
                if (response.isSuccessful){
                    val dataResponse = response.body()
                    result.value = ApiResponse.Success(dataResponse)
                }
                else{
                    result.value = ApiResponse.Error(response.message())
                    Log.e("RemoteDataSource", response.message())
                }
            }

            override fun onFailure(call: Call<ListMovieResponse>, t: Throwable) {
                result.value = ApiResponse.Error(t.toString())
                Log.e("RemoteDataSource", t.toString())
            }
        })
        return result
    }

    fun getDetailMovie(idMovie: String) : LiveData<ApiResponse<DetailMovieResponse?>>{
        val result = MutableLiveData<ApiResponse<DetailMovieResponse?>>()

        ApiConfig.getApiService().getDetailMovie(BuildConfig.API_KEY, idMovie).enqueue(object :
            Callback<DetailMovieResponse>{
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                if (response.isSuccessful){
                    val dataResponse = response.body()
                    result.value = ApiResponse.Success(dataResponse)
                }
                else{
                    result.value = ApiResponse.Error(response.message())
                    Log.e("RemoteDataSource", response.message())
                }
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                result.value = ApiResponse.Error(t.toString())
                Log.e("RemoteDataSource", t.toString())
            }
        })
        return result
    }
}