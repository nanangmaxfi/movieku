package id.nanangmaxfi.movieku.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by nanangmaxfi on 09/01/22.
 */

@Parcelize
data class Movie(
    val movieId : Int,
    val posterPath: String,
    val title: String,
    val releaseDate: String,
    val rating: Double,
    val isFavorite: Boolean
) : Parcelable
