package com.example.composemovieapp.cache.model

import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.domain.util.DomainMapper
import com.example.composemovieapp.network.model.Genre


class MovieEntityMapper : DomainMapper<MovieEntity, Movie>{
    override fun mapToDomainModel(model: MovieEntity): Movie {
        return Movie(
            id = model.id,
            backdrop_path = model.backdropPath,
            homepage = model.homepage,
            media_type = model.mediaType,
            overview = model.overview,
            runtime = model.runtime,
            original_title = model.originalTitle,
            original_language = model.originalLanguage,
            title = model.title,
            popularity = model.popularity,
            poster_path = model.posterPath,
            release_date = model.releaseDate,
            status = model.status,
            vote_count = model.voteCount,
            vote_average = model.voteAverage,



        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieEntity {
        return MovieEntity(
            id = domainModel.id,
            backdropPath = domainModel.backdrop_path,
            homepage = domainModel.homepage,
            mediaType = domainModel.media_type,
            originalLanguage = domainModel.original_language,
            originalTitle = domainModel.original_title,
            overview = domainModel.overview,
            popularity = domainModel.popularity,
            posterPath = domainModel.poster_path,
            releaseDate = domainModel.release_date,
            runtime = domainModel.runtime,
            status = domainModel.status,
            title = domainModel.title,
            voteAverage = domainModel.vote_average,
            voteCount = domainModel.vote_count
        )
    }

    fun fromEntityList(initial: List<MovieEntity>): List<Movie>{
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Movie>): List<MovieEntity>{
        return initial.map { mapFromDomainModel(it) }
    }


}

private fun convertGenreListToString(genres: List<Genre>): String {
    val genresString = StringBuilder()
    for(genre in genres){
        genresString.append("$genre,")
    }
    return genresString.toString()
}


