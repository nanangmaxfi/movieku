package id.nanangmaxfi.movieku.core.data.source.remote.network

import id.nanangmaxfi.movieku.core.data.source.remote.response.DetailMovieResponse
import id.nanangmaxfi.movieku.core.data.source.remote.response.ListMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("trending/movie/week?api_key={key}")
    fun getListTrendingMovie(@Path("key") apiKey: String) : Call<ListMovieResponse>

    @GET("movie/{id_movie}?api_key={key}")
    fun getDetailMovie(@Path("key") apiKey: String, @Path("id_movie") idMovie: String) : Call<DetailMovieResponse>
}