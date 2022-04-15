package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.view_models.details

import androidx.lifecycle.ViewModel
import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.retrofit.IMovieDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailsViewModel(private val api: IMovieDataSource) : ViewModel() {
    fun getMovieDetails(id: String): Flow<MovieDetails> = flow {
        val trailerData = api.trailer(id)
        val posterData = api.poster(id)
        val ratingData = api.ratings(id)
        val wikipediaData = api.wikipedia(id)
        emit(
            MovieDetails(
                id = id,
                title = trailerData.title,
                year = trailerData.year,
                score = ratingData.imDb,
                poster = if(posterData.posters.isEmpty()) null else posterData.posters[0].link,
                thumbnail = trailerData.thumbnailUrl,
                overview = wikipediaData.plotShort?.plainText
            )
        )
    }
}