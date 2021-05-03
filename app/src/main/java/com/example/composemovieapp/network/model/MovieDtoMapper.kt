package com.example.composemovieapp.network.model

import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.domain.util.DomainMapper


class MovieDtoMapper : DomainMapper<MovieDto, Movie> {


    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(

            backdrop_path = model.backdropPath,
            genres = model.genres,
            id = model.id,
            original_language = model.originalLanguage,
            original_title =  model.originalTitle,
            overview = model.overview,
            media_type = model.mediaType,
            popularity = model.popularity,
            poster_path = model.posterPath,
            releaseDate = model.releaseDate,
            runtime = model.runtime,
            title = model.title,
            vote_average = model.voteAverage,
            vote_count =  model.voteCount,
            status =  model.status
        )
    }

    /**
     * We use this function when publishing to the network
     */
    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
       return MovieDto(

           backdropPath = domainModel.backdrop_path ,
           genres = domainModel.genres,
           id = domainModel.id,
           mediaType = domainModel.media_type,
           originalLanguage = domainModel.original_language,
           originalTitle = domainModel.original_title,
           overview = domainModel.overview,
           popularity =  domainModel.popularity,
           posterPath = domainModel.poster_path,
           releaseDate = domainModel.releaseDate,
           runtime =domainModel.runtime,
           title = domainModel.title,
           voteAverage = domainModel.vote_average,
           voteCount = domainModel.vote_count,
           status = domainModel.status

       )

       }

    fun toDomainList(initial: List<MovieDto>): List<Movie>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Movie>): List<MovieDto>{
        return initial.map { mapFromDomainModel(it) }
    }


    }



