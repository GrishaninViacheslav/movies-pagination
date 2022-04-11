package io.github.grishaninvyacheslav.theatre_movies_pagination.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import java.io.IOException

private const val TMDB_STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val service: MovieService
) : PagingSource<Int, RankedMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RankedMovie> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = service.getTopRatedMovies(
                page = pageIndex
            )
            val prevKey = if (pageIndex > 1) pageIndex - 1 else null
            val nextKey = if (response.isNotEmpty()) pageIndex + 1 else null
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey,
                itemsBefore = (pageIndex - 1) * NETWORK_PAGE_SIZE,
                itemsAfter = with(NETWORK_ITEMS_COUNT - pageIndex*NETWORK_PAGE_SIZE){ if(this < 0) 0 else this }
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RankedMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}