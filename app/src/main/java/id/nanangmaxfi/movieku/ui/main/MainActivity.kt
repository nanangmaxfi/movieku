package id.nanangmaxfi.movieku.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import id.nanangmaxfi.movieku.R
import id.nanangmaxfi.movieku.core.data.source.Resource
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.core.ui.ViewModelFactory
import id.nanangmaxfi.movieku.core.utils.AppUtils
import id.nanangmaxfi.movieku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppUtils.actionbar(this, "Trending Movie")

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]


        viewModel.movieList.observe(this, { response ->
            if (response != null){
                when(response){
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        showListMovie(response.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = response.message ?: "Something error"
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