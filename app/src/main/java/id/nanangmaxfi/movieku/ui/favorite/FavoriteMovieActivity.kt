package id.nanangmaxfi.movieku.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupieAdapter
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.core.ui.ViewModelFactory
import id.nanangmaxfi.movieku.core.utils.AppUtils
import id.nanangmaxfi.movieku.databinding.ActivityFavoriteMovieBinding
import id.nanangmaxfi.movieku.ui.main.MovieItem

class FavoriteMovieActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteMovieBinding
    private lateinit var viewModel: FavoriteMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppUtils.actionbarWithBack(this, "Favorite Movie")

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

        viewModel.movieListFavorite.observe(this, { response ->
            if (response != null && response.isNotEmpty()){
                showListMovie(response)
            }
            else{
                binding.viewError.root.visibility = View.VISIBLE
                binding.viewError.tvError.text = "Belum ada movie favorite"
            }
        })
    }

    private fun showListMovie(listMovie : List<Movie>){
        val adapter = GroupieAdapter()
        listMovie.forEach {
            adapter.add(MovieItem(it))
        }
        binding.rvMovie.layoutManager = GridLayoutManager(this,2)
        binding.rvMovie.adapter = adapter
    }
}