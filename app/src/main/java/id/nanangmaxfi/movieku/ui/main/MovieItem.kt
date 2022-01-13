package id.nanangmaxfi.movieku.ui.main

import android.view.View
import com.bumptech.glide.Glide
import com.xwray.groupie.viewbinding.BindableItem
import id.nanangmaxfi.movieku.BuildConfig
import id.nanangmaxfi.movieku.R
import id.nanangmaxfi.movieku.core.domain.model.Movie
import id.nanangmaxfi.movieku.databinding.ItemMovieBinding

class MovieItem(private val movie: Movie) : BindableItem<ItemMovieBinding>() {
    override fun bind(binding: ItemMovieBinding, item: Int) {
        with(binding) {
            textTitle.text = movie.title
            textReleaseDate.text = movie.releaseDate
            textRating.text = movie.rating.toString()
            Glide.with(root.context)
                .load(BuildConfig.IMAGE_URL + movie.posterPath)
                .into(imagePoster)
        }
    }

    override fun getLayout(): Int = R.layout.item_movie

    override fun initializeViewBinding(view: View): ItemMovieBinding {
       return ItemMovieBinding.bind(view)
    }
}