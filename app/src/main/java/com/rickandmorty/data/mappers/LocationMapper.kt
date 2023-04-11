package com.rickandmorty.data.mappers

import com.rickandmorty.data.datasource.remote.location.entity.LocationDto
import com.rickandmorty.domain.model.location.Info
import com.rickandmorty.domain.model.location.Location
import com.rickandmorty.domain.model.location.Results

fun LocationDto.toLocation(): Location {
    val result: ArrayList<Results> = arrayListOf()

    results.forEach {
        result.add(
            Results(
                id = it.id ?: 0,
                name = it.name ?: "",
                dimension = it.dimension ?: "",
                createdDate = it.createdDate ?: "",
                type = it.type ?: "",
                url = it.url ?: "",
                residents = it.residents
            )
        )
    }


    return Location(
        info = Info(
            count = info?.count ?: 0,
            pages = info?.pages ?: 0,
            nextPageUrl = info?.nextPageUrl ?: "",
            prePageUrl = info?.prevPageUrl ?: ""
        ),
        results = result
    )
}