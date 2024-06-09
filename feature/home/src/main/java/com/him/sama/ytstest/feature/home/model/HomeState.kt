package com.him.sama.ytstest.feature.home.model


data class UiPoint(
    val x: Int,
    val y: Int,
    val value: Int,
    val isVisited: Boolean = false,
    val isPath: Boolean = false
)

enum class Algorithm(val algoName: String) {
    BFS("BFS"),
    DFS("DFS (Not enough time)"),
    AStar("AStar (Not enough time)"),
    Djikstra("Djikstra (Not enough time)")
}

enum class SampleData(val dataName: String) {
    Small("Small (5x5)"),
    Large("Large (1000x1000)"),
    NoPath("NoPath"),
    MultiplePath("Mutiple path"),
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val rowSize: Int = 0,
    val columnSize: Int = 0,
    val uiPoints: List<UiPoint> = listOf(),
    val selectedAlgorithm: Algorithm = Algorithm.BFS,
    val selectedSampleData: SampleData = SampleData.Small
)