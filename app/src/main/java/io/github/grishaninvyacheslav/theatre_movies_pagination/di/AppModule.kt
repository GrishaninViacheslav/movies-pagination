package io.github.grishaninvyacheslav.theatre_movies_pagination.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import io.github.grishaninvyacheslav.theatre_movies_pagination.BuildConfig
import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.retrofit.IMovieDataSource
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.screens.IScreens
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.screens.Screens
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.view_models.details.DetailsViewModel
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.view_models.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }

    factory { provideRetrofit() }
    single { provideNetworkApi(get()) }

    single { provideCicerone() }
    single { provideRouter(get()) }
    single { provideScreens() }
}

fun provideCicerone(): Cicerone<Router> = Cicerone.create()
fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router
fun provideScreens(): IScreens = Screens()

fun provideRetrofit(): Retrofit {
    val interceptor = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }
    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url
            val url =
                originalHttpUrl.newBuilder().setPathSegment(3, BuildConfig.IMDB_API_KEY)
                    .build()
            request.url(url)
            return@addInterceptor chain.proceed(request.build())
        }
        .build()

    return Retrofit.Builder()
        .baseUrl(BuildConfig.IMDB_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

}

fun provideNetworkApi(retrofit: Retrofit): IMovieDataSource =
    retrofit.create(IMovieDataSource::class.java)