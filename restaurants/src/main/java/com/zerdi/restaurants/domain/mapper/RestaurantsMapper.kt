package com.zerdi.restaurants.domain.mapper

import androidx.annotation.VisibleForTesting
import com.zerdi.restaurants.data.local.model.RestaurantsDto
import com.zerdi.restaurants.data.local.model.RestaurantsDto.RestaurantDto
import com.zerdi.restaurants.data.local.model.RestaurantsDto.RestaurantDto.SortingValuesDto
import com.zerdi.restaurants.domain.model.Restaurants
import com.zerdi.restaurants.domain.model.Restaurants.Restaurant
import com.zerdi.restaurants.domain.model.Restaurants.Restaurant.SortingValues
import com.zerdi.restaurants.domain.model.Status
import javax.inject.Inject

class RestaurantsMapper @Inject constructor() {

    fun mapToRestaurants(dto: RestaurantsDto) = Restaurants(
        restaurants = dto.restaurants?.map(::mapToRestaurant).orEmpty()
    )

    @VisibleForTesting
    fun mapToRestaurant(dto: RestaurantDto): Restaurant = Restaurant(
        id = dto.id.toDouble(),
        name = dto.name,
        status = mapToStatus(dto.status),
        sortingValues = mapToSortingValue(dto.sortingValues)
    )

    @VisibleForTesting
    fun mapToStatus(status: String?) =
        Status.values().find {
            it.toString().lowercase().replace("_", " ") == status
        } ?: Status.CLOSED

    @VisibleForTesting
    fun mapToSortingValue(dto: SortingValuesDto): SortingValues = SortingValues(
        bestMatch = dto.bestMatch,
        deliveryCosts = dto.deliveryCosts.toDouble(),
        distance = dto.distance.toDouble(),
        minCost = dto.minCost.toDouble(),
        newest = dto.newest,
        popularity = dto.popularity,
        ratingAverage = dto.ratingAverage,
        averageProductPrice = dto.averageProductPrice.toDouble()
    )
}
