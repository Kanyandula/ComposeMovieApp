package com.example.composemovieapp.network.model

import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.domain.util.DomainMapper
import com.google.gson.annotations.SerializedName

class MovieDtoMapper : DomainMapper<MovieDto, Movie> {


    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
            adult = model.adult,
            backdrop_path = model.backdropPath,
            genre_ids = model.genreIds,
            genres = model.genres,
            id = model.id,
            original_language = model.originalLanguage,
            original_title =  model.originalTitle,
            overview = model.overview,
            mediaType = model.mediaType,
            popularity = model.popularity,
            poster_path = model.posterPath,
            releaseDate = model.releaseDate,
            runtime = model.runtime,
            title = model.title,
            video = model.video,
            vote_average = model.voteAverage,
            vote_count =  model.voteCount
        )
    }

    /**
     * We use this function when publishing to the network
     */
    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
       return MovieDto(
           adult = domainModel.adult,
           backdropPath = domainModel.backdrop_path ,
           genreIds = domainModel.genre_ids,
           genres = domainModel.genres,
           id = domainModel.id,
           mediaType = domainModel.mediaType,
           originalTitle = domainModel.original_title,
           overview = domainModel.overview,
           popularity =  domainModel.popularity,
           posterPath = domainModel.poster_path,
           releaseDate = domainModel.releaseDate,
           runtime =domainModel.runtime,
           title = domainModel.title,
           video = domainModel.video,
           voteAverage = domainModel.vote_average,
           voteCount = domainModel.vote_count

       )
       }

    fun toDomainList(initial: List<MovieDto>): List<Movie>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Movie>): List<MovieDto>{
        return initial.map { mapFromDomainModel(it) }
    }


    }



