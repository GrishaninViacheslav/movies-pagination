package io.github.grishaninvyacheslav.theatre_movies_pagination.di

import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.retrofit.IMovieDataSource
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.view_models.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    viewModel { MainViewModel(get()) }

    factory { provideRetrofit() }
    single { provideNetworkApi(get()) }
}

fun provideRetrofit(): Retrofit {
    val interceptor = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }
    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    return Retrofit.Builder()
        .baseUrl(NetworkConstant.IMDB_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

}

fun provideNetworkApi(retrofit: Retrofit): IMovieDataSource =
    retrofit.create(IMovieDataSource::class.java)