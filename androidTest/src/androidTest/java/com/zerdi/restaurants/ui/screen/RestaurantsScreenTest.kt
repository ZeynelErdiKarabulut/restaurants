package com.zerdi.restaurants.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.zerdi.androidtest.R
import com.zerdi.core.domain.model.Result
import com.zerdi.restaurants.RestaurantsFactory.RestaurantsTestScreen
import com.zerdi.restaurants.RestaurantsFactory.getMockRestaurantResult
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class RestaurantsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testErrorScenario() {
        val message = "Timeout"
        val result = mutableStateOf(Result.Error(IOException(message)))

        composeTestRule.setContent {
            RestaurantsTestScreen(result = result)
        }

        composeTestRule
            .onNodeWithText(message)
            .assertIsDisplayed()
    }

    @Test
    fun testLoadingScenario() {
        lateinit var processTag: String
        val result = mutableStateOf(Result.Loading)

        composeTestRule.setContent {
            RestaurantsTestScreen(result = result)

            processTag = stringResource(id = R.string.test_progress_tag)
        }

        composeTestRule
            .onNodeWithTag(processTag)
            .assertIsDisplayed()
    }

    @Test
    fun testSuccessScenario() {
        lateinit var inputTag: String

        composeTestRule.setContent {
            RestaurantsTestScreen(result = getMockRestaurantResult())

            inputTag = stringResource(id = R.string.input)
        }

        composeTestRule
            .onNodeWithTag(inputTag)
            .assertIsDisplayed()
    }
}
