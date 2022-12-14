package com.zerdi.restaurants.ui.screen

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zerdi.core.compose.Orange
import com.zerdi.core.compose.White
import com.zerdi.restaurants.R
import com.zerdi.restaurants.domain.model.Restaurants.Restaurant
import com.zerdi.restaurants.domain.model.SearchSortFilter
import com.zerdi.restaurants.domain.model.SearchSortFilter.Sorting
import com.zerdi.restaurants.ui.component.FiltersComponent
import com.zerdi.restaurants.ui.component.RestaurantList
import com.zerdi.restaurants.ui.component.SearchableToolbar
import com.zerdi.util.noRippleClickable
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Suppress("LongMethod")
fun RestaurantListScreen(
    restaurants: List<Restaurant>,
    sortFilterState: State<SearchSortFilter>,
    onSetSorting: (Sorting) -> Unit,
    refreshState: State<Boolean>,
    onRefresh: () -> Unit,
    onQueryUpdated: (String) -> Unit
) {

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val localFocusManager = LocalFocusManager.current
    val filterBottomSheetState = rememberModalBottomSheetState(Hidden)
    val activity = LocalContext.current as ComponentActivity
    var icon by remember { mutableStateOf(Icons.Filled.FilterList) }

    BackHandler {
        if (filterBottomSheetState.isVisible) {
            coroutineScope.launch {
                filterBottomSheetState.hide()
            }
        } else activity.finish()
    }

    ModalBottomSheetLayout(
        sheetState = filterBottomSheetState,
        sheetContent = {
            FiltersComponent(sortFilterState) {
                onSetSorting.invoke(it)
                coroutineScope.launch(Main) {
                    scrollState.animateScrollToItem(0)
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetElevation = 4.dp,
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshState.value),
            onRefresh = {
                coroutineScope.launch(Main) {
                    scrollState.animateScrollToItem(0)
                }
                onRefresh.invoke()
            }
        ) {

            Column(modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onPress = { localFocusManager.clearFocus(true) })
                }) {

                Spacer(
                    modifier = Modifier
                        .background(Orange)
                        .statusBarsHeight()
                        .fillMaxWidth()
                )

                icon = if (restaurants.isEmpty()) Icons.Filled.ClearAll
                else Icons.Filled.FilterList

                SearchableToolbar(
                    sortFilter = sortFilterState,
                    onQueryUpdated = onQueryUpdated::invoke,
                    Icon = {
                        Crossfade(icon) {
                            Icon(
                                imageVector = it,
                                contentDescription = stringResource(id = R.string.sort),
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .noRippleClickable {
                                        if (restaurants.isEmpty()) {
                                            coroutineScope.launch(Main) {
                                                scrollState.animateScrollToItem(0)
                                            }
                                            onRefresh.invoke()
                                        } else {
                                            coroutineScope.launch {
                                                localFocusManager.clearFocus(true)
                                                filterBottomSheetState.show()
                                            }
                                        }
                                    },
                                tint = White
                            )
                        }
                    })

                if (restaurants.isEmpty()) {
                    EmptyScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                    )
                } else {
                    RestaurantList(restaurants, scrollState)
                }
            }
        }
    }
}
