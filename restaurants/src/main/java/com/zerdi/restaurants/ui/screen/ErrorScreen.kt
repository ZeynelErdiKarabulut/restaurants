package com.zerdi.restaurants.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zerdi.core.compose.Red
import com.zerdi.restaurants.R

@Composable
fun ErrorScreen(
    errorMessage: String?
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = errorMessage ?: stringResource(id = R.string.genericErrorMessage),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.body1,
            color = Red
        )
    }
}
