package id.nanangmaxfi.movieku.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupieAdapter
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.core.utils.AppUtils
import id.nanangmaxfi.movieku.favorite.databinding.ActivityFavoriteMovieBinding
import id.nanangmaxfi.movieku.ui.main.MovieItem
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteMovieActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteMovieBinding
    private val viewModel: FavoriteMovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(favoriteModule)
        AppUtils.actionbarWithBack(this, "Favorite Movie")

        viewModel.movieListFavorite.observe(this, { response ->
            if (response != null && response.isNotEmpty()){
                showListMovie(response)
            }
            else{
                binding.rvMovie.visibility = View.GONE
                binding.viewError.root.visibility = View.VISIBLE
                binding.viewError.tvError.text = "Belum ada movie favorite"
            }
        })
    }

    private fun showListMovie(listMovie : List<Movie>){
        binding.viewError.root.visibility = View.GONE
        binding.rvMovie.visibility = View.VISIBLE

        val adapter = GroupieAdapter()
        listMovie.forEach {
            adapter.add(MovieItem(it))
        }
        binding.rvMovie.layoutManager = GridLayoutManager(this,2)
        binding.rvMovie.adapter = adapter
    }
}