package com.zerdi.restaurants.data

import com.zerdi.restaurants.data.local.model.RestaurantsDto

interface RestaurantsDataSource {

    interface Memory {
        fun cacheInMemory(dto: RestaurantsDto)

        fun fetchRestaurants(): RestaurantsDto?
    }

    interface Local {
        suspend fun fetchRestaurants(): RestaurantsDto
    }
}
