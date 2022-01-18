package id.nanangmaxfi.movieku.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupieAdapter
import id.nanangmaxfi.movieku.R
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.core.utils.AppUtils
import id.nanangmaxfi.movieku.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppUtils.actionbar(this, getString(R.string.trending_movie))

        viewModel.movieList.observe(this, { response ->
            if (response != null){
                when(response){
                    is id.nanangmaxfi.movieku.core.data.source.Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is id.nanangmaxfi.movieku.core.data.source.Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        showListMovie(response.data)
                    }
                    is id.nanangmaxfi.movieku.core.data.source.Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = response.message ?: getString(R.string.something_error)
                    }
                }
            }
        })
    }

    private fun showListMovie(listMovie : List<Movie>?){
        if (listMovie == null) return
        val adapter = GroupieAdapter()
        listMovie.forEach {
            adapter.add(MovieItem(it))
        }
        binding.rvMovie.layoutManager = GridLayoutManager(this,2)
        binding.rvMovie.adapter = adapter
    }
}