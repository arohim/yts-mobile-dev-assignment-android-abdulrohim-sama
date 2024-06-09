package com.him.sama.ytstest.feature.home.model


data class UiPoint(
    val x: Int,
    val y: Int,
    val isVisited: Boolean = false,
    val isPath: Boolean = false
)

data class HomeUiState(
    val isLoading: Boolean = false,
    val rowSize: Int = 0,
    val columnSize: Int = 0,
    val uiPoints: List<UiPoint> = listOf()
)