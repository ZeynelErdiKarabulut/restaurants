package com.zerdi.restaurants.data.local

import android.content.res.AssetManager
import com.zerdi.core.di.qualifier.IoDispatcher
import com.zerdi.restaurants.data.local.model.RestaurantsDto
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class RestaurantsProvider @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val json: Json,
    private val assetManager: AssetManager
) {

    @Suppress("BlockingMethodInNonBlockingContext")
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun fetchRestaurants(): RestaurantsDto = withContext(ioDispatcher) {
        json.decodeFromStream(
            stream = assetManager.open("restaurants.json")
        )
    }
}
