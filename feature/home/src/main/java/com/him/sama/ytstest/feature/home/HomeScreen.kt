package com.him.sama.ytstest.feature.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.him.sama.ytstest.common.ui.theme.SpotifyJetpackComposeTheme
import com.him.sama.ytstest.feature.home.model.HomeUiState
import com.him.sama.ytstest.feature.home.model.UiPoint

@Composable
fun HomeScreen() {
    Body(testUiState)
}

@Composable
private fun Body(uiState: HomeUiState) {
    val row = uiState.rowSize
    val column = uiState.columnSize
    val defaultLineColor = Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            verticalArrangement = Arrangement.Center,
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                val rowSize = width / row
                val columnSize = height / column

                uiState.uiPoints.forEach {
                    val color = if (it.isPath) {
                        Color.Blue
                    } else if (it.isVisited) {
                        Color.Red
                    } else {
                        defaultLineColor
                    }
                    val style = if (it.isVisited) {
                        Fill
                    } else {
                        Stroke()
                    }
                    drawRect(
                        color = color,
                        topLeft = Offset(it.x * rowSize, it.y * columnSize),
                        size = Size(width = rowSize, height = columnSize),
                        style = style
                    )
                }
            }
        }
    }
}

val testUiState = HomeUiState(
    rowSize = 4,
    columnSize = 4,
    uiPoints = listOf(
        UiPoint(x = 0, y = 0, isVisited = true, isPath = true),
        UiPoint(x = 0, y = 1, isVisited = false, isPath = false),
        UiPoint(x = 0, y = 2, isVisited = false, isPath = false),
        UiPoint(x = 0, y = 3, isVisited = false, isPath = false),
        UiPoint(x = 1, y = 0, isVisited = true, isPath = true),
        UiPoint(x = 1, y = 1, isVisited = true, isPath = false),
        UiPoint(x = 1, y = 2, isVisited = false, isPath = false),
        UiPoint(x = 1, y = 3, isVisited = false, isPath = false),
        UiPoint(x = 2, y = 0, isVisited = true, isPath = true),
        UiPoint(x = 2, y = 1, isVisited = true, isPath = false),
        UiPoint(x = 2, y = 2, isVisited = true, isPath = false),
        UiPoint(x = 2, y = 3, isVisited = true, isPath = false),
        UiPoint(x = 3, y = 0, isVisited = true, isPath = true),
        UiPoint(x = 3, y = 1, isVisited = true, isPath = true),
        UiPoint(x = 3, y = 2, isVisited = true, isPath = true),
        UiPoint(x = 3, y = 3, isVisited = true, isPath = true)
    )
)

@Preview
@Composable
fun PreviewBody() {
    SpotifyJetpackComposeTheme {
        Body(testUiState)
    }
}
