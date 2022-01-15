package id.nanangmaxfi.movieku.core.utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import id.nanangmaxfi.movieku.R
import id.nanangmaxfi.movieku.ui.favorite.FavoriteMovieActivity

object AppUtils {
    //actionbar default
    fun actionbar(activity: AppCompatActivity, title: String){
        activity.supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        activity.supportActionBar?.setDisplayShowCustomEnabled(true)
        activity.supportActionBar?.setCustomView(R.layout.toolbar_custom)
        val view =activity.supportActionBar?.customView
        val titleView = view?.findViewById<TextView>(R.id.txt_title)
        titleView?.text = title

        val optionView = view?.findViewById<ImageView>(R.id.img_option)
        optionView?.visibility = View.GONE

        val backView = view?.findViewById<ImageView>(R.id.img_back)
        backView?.visibility = View.GONE

        val favoriteView = view?.findViewById<ImageView>(R.id.img_favorite)
        favoriteView?.visibility = View.VISIBLE
        favoriteView?.setOnClickListener {
            val intent = Intent(activity, FavoriteMovieActivity::class.java)
            activity.startActivity(intent)
        }
    }

    //actionbar with back
    fun actionbarWithBack(activity: AppCompatActivity, title: String){
        activity.supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        activity.supportActionBar?.setDisplayShowCustomEnabled(true)
        activity.supportActionBar?.setCustomView(R.layout.toolbar_custom)
        val view =activity.supportActionBar?.customView
        val titleView = view?.findViewById<TextView>(R.id.txt_title)
        titleView?.text = title

        val optionView = view?.findViewById<ImageView>(R.id.img_option)
        optionView?.visibility = View.GONE

        val favoriteView = view?.findViewById<ImageView>(R.id.img_favorite)
        favoriteView?.visibility = View.GONE

        val backView = view?.findViewById<ImageView>(R.id.img_back)
        backView?.visibility = View.VISIBLE
        backView?.setOnClickListener { activity.finish() }
    }

}