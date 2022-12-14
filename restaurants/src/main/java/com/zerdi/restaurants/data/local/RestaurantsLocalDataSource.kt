package com.zerdi.restaurants.data.local

import com.zerdi.restaurants.data.RestaurantsDataSource
import com.zerdi.restaurants.data.local.model.RestaurantsDto
import javax.inject.Inject
import kotlinx.coroutines.delay
import timber.log.Timber

class RestaurantsLocalDataSource @Inject constructor(
    private val restaurantsProvider: RestaurantsProvider
) : RestaurantsDataSource.Local {

    override suspend fun fetchRestaurants(): RestaurantsDto {
        val restaurants = restaurantsProvider.fetchRestaurants()
        delay(750)
        Timber.d("restaurants fetched")
        return restaurants
    }
}
