package id.nanangmaxfi.movieku.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.nanangmaxfi.movieku.core.data.source.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovie(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movie where isFavorite = 1")
    fun getFavoriteMovie(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(tourism: List<MovieEntity>)

    @Update
    fun updateFavoriteMovie(tourism: MovieEntity)
}