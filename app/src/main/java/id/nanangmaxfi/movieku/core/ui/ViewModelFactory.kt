package id.nanangmaxfi.movieku.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.nanangmaxfi.movieku.core.di.Injection
import id.nanangmaxfi.movieku.core.domain.usecase.MovieUseCase
import id.nanangmaxfi.movieku.ui.main.MainViewModel

class ViewModelFactory private constructor(private val moviewUseCase: MovieUseCase) : ViewModelProvider.NewInstanceFactory(){
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideTourismUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(moviewUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}