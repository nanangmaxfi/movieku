package id.nanangmaxfi.movieku.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import id.nanangmaxfi.movieku.BuildConfig
import id.nanangmaxfi.movieku.R
import id.nanangmaxfi.movieku.core.data.source.Resource
import id.nanangmaxfi.movieku.core.domain.model.MovieDetail
import id.nanangmaxfi.movieku.core.ui.ViewModelFactory
import id.nanangmaxfi.movieku.core.utils.AppUtils
import id.nanangmaxfi.movieku.databinding.ActivityDetailMovieBinding

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppUtils.actionbarWithBack(this, "Detail Movie")

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        val movieId = intent.extras?.getInt("movieId")
        if(movieId != null){
            viewModel.movieDetail(movieId).observe(this, { response ->
                if (response != null){
                    when(response){
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.scrollView.visibility = View.GONE
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.scrollView.visibility = View.VISIBLE
                            if (response.data != null) {
                                val statusFavorite = intent.extras?.getBoolean("statusFavorite")
                                showMovie(response.data, statusFavorite?:false)
                            }
                            else
                                showError("Empty Data")
                        }
                        is Resource.Error -> {
                            showError(response.message ?: "Something error")
                        }
                    }
                }
            })
        }

    }

    private fun showError(message: String){
        binding.progressBar.visibility = View.GONE
        binding.scrollView.visibility = View.GONE
        binding.viewError.root.visibility = View.VISIBLE
        binding.viewError.tvError.text = message
    }

    private fun showMovie(movie: MovieDetail, isFavorite: Boolean){
        with(binding){
            textTitle.text = movie.title
            textReleaseDate.text = movie.releaseDate
            textRating.text = movie.rating.toString()
            Glide.with(root.context)
                .load(BuildConfig.IMAGE_URL + movie.posterPath)
                .into(imagePoster)

            countVote.text = movie.countVote.toString()
            textTagline.text = movie.tagline
            textGenre.text = movie.genre
            textStatus.text = movie.status
            textHomepage.text = movie.homepage
            textCountry.text = movie.countries
            textProduction.text = movie.production
            textOverview.text = movie.overview

            var statusFavorite = isFavorite
            setStatusFavorite(statusFavorite)
            binding.fabFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                viewModel.setFavoriteMovie(movie, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean){
        if (statusFavorite){
            binding.fabFavorite.setIconResource(R.drawable.ic_favorite)
            binding.fabFavorite.text = "Remove favorite"
        }
        else{
            binding.fabFavorite.setIconResource(R.drawable.ic_favorite_border)
            binding.fabFavorite.text = "Save favorite"
        }
    }
}