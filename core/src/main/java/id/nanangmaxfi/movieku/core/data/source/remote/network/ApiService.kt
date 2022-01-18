package id.nanangmaxfi.movieku.core.data.source.remote.network

import id.nanangmaxfi.movieku.core.data.source.remote.response.DetailMovieResponse
import id.nanangmaxfi.movieku.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/week")
    suspend fun getListTrendingMovie(@Query("api_key") apiKey: String) : ListMovieResponse

    @GET("movie/{id_movie}")
    suspend fun getDetailMovie(@Path("id_movie") idMovie: String, @Query("api_key") apiKey: String) : DetailMovieResponse
}