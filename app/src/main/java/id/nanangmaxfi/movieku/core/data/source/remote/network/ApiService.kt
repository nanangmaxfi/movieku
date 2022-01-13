package id.nanangmaxfi.movieku.core.data.source.remote.network

import id.nanangmaxfi.movieku.core.data.source.remote.response.DetailMovieResponse
import id.nanangmaxfi.movieku.core.data.source.remote.response.ListMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/week")
    fun getListTrendingMovie(@Query("api_key") apiKey: String) : Call<ListMovieResponse>

    @GET("movie/{id_movie}")
    fun getDetailMovie(@Query("api_key") apiKey: String, @Path("id_movie") idMovie: String) : Call<DetailMovieResponse>
}