package io.github.grishaninvyacheslav.theatre_movies_pagination.domain.retrofit

data class WikipediaData(val plotShort: PlotShortData?){
    data class PlotShortData(val plainText: String, val html: String)
}

