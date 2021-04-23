package com.example.composemovieapp.domain.util

import com.example.composemovieapp.network.model.Genre

interface DomainMapper <T, DomainModel>{

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T


}