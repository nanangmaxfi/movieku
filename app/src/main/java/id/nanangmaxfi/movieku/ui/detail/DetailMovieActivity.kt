package id.nanangmaxfi.movieku.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import id.nanangmaxfi.movieku.BuildConfig
import id.nanangmaxfi.movieku.R
import id.nanangmaxfi.movieku.core.domain.model.MovieDetail
import id.nanangmaxfi.movieku.core.utils.AppUtils
import id.nanangmaxfi.movieku.databinding.ActivityDetailMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel: DetailMovieViewModel by viewModel()

    companion object{
        const val MOVIE_ID = "movieId"
        const val STATUS_FAVORITE = "statusFavorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppUtils.actionbarWithBack(this, getString(R.string.detail_movie))

        val movieId = intent.extras?.getInt(MOVIE_ID)
        if(movieId != null){
            viewModel.movieDetail(movieId).observe(this, { response ->
                if (response != null){
                    when(response){
                        is id.nanangmaxfi.movieku.core.data.source.Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.scrollView.visibility = View.GONE
                        }
                        is id.nanangmaxfi.movieku.core.data.source.Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.scrollView.visibility = View.VISIBLE
                            if (response.data != null) {
                                val statusFavorite = intent.extras?.getBoolean(STATUS_FAVORITE)
                                showMovie(response.data!!, statusFavorite?:false)
                            }
                            else
                                showError(getString(R.string.empty_data))
                        }
                        is id.nanangmaxfi.movieku.core.data.source.Resource.Error -> {
                            showError(response.message ?: getString(R.string.something_error))
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
            binding.fabFavorite.text = getString(R.string.remove_favorite)
        }
        else{
            binding.fabFavorite.setIconResource(R.drawable.ic_favorite_border)
            binding.fabFavorite.text = getString(R.string.save_favorite)
        }
    }
}