package com.zerdi.restaurants.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zerdi.restaurants.domain.model.SearchSortFilter
import com.zerdi.restaurants.navigation.Screen
import com.zerdi.restaurants.ui.screen.RestaurantsScreen

@Composable
fun RestaurantsApp() {
    val navigation = rememberNavController()

    NavHost(
        navController = navigation,
        startDestination = Screen.RestaurantsScreen.route
    ) {
        composable(
            route = Screen.RestaurantsScreen.route
        ) {
            val viewModel = hiltViewModel<RestaurantsViewModel>()

            RestaurantsScreen(
                result = viewModel
                    .restaurantsFlow
                    .collectAsState(),
                sortFilterState = viewModel
                    .getSortStateFlow()
                    .collectAsState(SearchSortFilter()),
                onSetSorting = viewModel::setSorting,
                refreshState = viewModel
                    .getRefreshState()
                    .collectAsState(),
                onRefresh = viewModel::refresh,
                onQueryUpdated = viewModel::setQuery,
            )
        }
    }
}
