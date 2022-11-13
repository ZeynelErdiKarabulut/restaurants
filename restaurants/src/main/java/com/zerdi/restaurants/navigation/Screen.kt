package com.zerdi.restaurants.navigation

sealed class Screen(val route: String){
    object RestaurantsScreen: Screen("restaurans")
}