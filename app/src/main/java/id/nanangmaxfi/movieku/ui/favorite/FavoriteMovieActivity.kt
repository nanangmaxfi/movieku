package id.nanangmaxfi.movieku.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.nanangmaxfi.movieku.databinding.ActivityFavoriteMovieBinding

class FavoriteMovieActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_favorite_movie)
    }
}