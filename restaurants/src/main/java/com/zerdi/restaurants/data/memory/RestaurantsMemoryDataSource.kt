package com.zerdi.restaurants.data.memory

import com.zerdi.restaurants.data.RestaurantsDataSource
import com.zerdi.restaurants.data.local.model.RestaurantsDto
import javax.inject.Inject
import timber.log.Timber

class RestaurantsMemoryDataSource @Inject constructor() :
    RestaurantsDataSource.Memory {

    private var restaurantsDto: RestaurantsDto? = null

    override fun cacheInMemory(dto: RestaurantsDto) {
        restaurantsDto = dto
    }

    override fun fetchRestaurants(): RestaurantsDto? {
        if (restaurantsDto != null) Timber.d("cache hit")
        return restaurantsDto
    }
}
