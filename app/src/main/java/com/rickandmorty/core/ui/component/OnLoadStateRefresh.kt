package com.rickandmorty.core.ui.component

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.rickandmorty.core.ui.theme.NeonGreen400
import com.rickandmorty.data.datasource.remote.location.entity.Results
import com.rickandmorty.presentation.main.OrientationState

/**
 * This method is a Lazy Load.
 * This method shows up when loadState is refresh.
 * When loadState is refresh, this method shows a circular indicator to the user.
 *
 * @param locations paging data for the loadState.
 * @param modifier the [Modifier] to be applied to this method.
 * @param setCharacterStateError called when loadState is error.
 */
fun LazyListScope.onLoadStateRefresh(
    locations: LazyPagingItems<Results>,
    modifier: Modifier,
    setCharacterStateError: () -> Unit
) {
    when (val state = locations.loadState.refresh) {
        is LoadState.Error -> {
            setCharacterStateError()
            Log.e("load state refresh error", state.error.stackTraceToString())
            item {
                ErrorPagingItem(modifier = modifier)
            }
        }
        is LoadState.Loading -> {
            item {
                Box(
                    modifier = modifier.fillMaxWidth().padding(
                        vertical = if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT)
                            PORTRAIT_HEIGHT / 4
                        else
                            0.dp
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = NeonGreen400)
                }
            }
        }
        else -> {}
    }
}