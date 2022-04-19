package io.github.grishaninvyacheslav.theatre_movies_pagination

import android.app.Application
import org.koin.core.context.startKoin
import io.github.grishaninvyacheslav.theatre_movies_pagination.di.appModule
import org.koin.android.ext.koin.androidContext

class TheatreMoviesPaginationApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TheatreMoviesPaginationApp)
            modules(appModule)
        }
    }
}