package id.nanangmaxfi.movieku

import android.app.Application
import id.nanangmaxfi.movieku.core.di.databaseModule
import id.nanangmaxfi.movieku.core.di.networkModule
import id.nanangmaxfi.movieku.core.di.repositoryModule
import id.nanangmaxfi.movieku.di.useCaseModule
import id.nanangmaxfi.movieku.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}