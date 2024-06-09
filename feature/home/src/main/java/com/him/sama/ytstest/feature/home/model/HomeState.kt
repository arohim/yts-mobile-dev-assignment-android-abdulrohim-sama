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
    DFS("DFS (Not enough time to implement)"),
    AStar("AStar (Not enough time to implement)"),
    Djikstra("Djikstra (Not enough time to implement)")
}

enum class SampleData(val dataName: String, val grid: Array<Array<Int>>) {
    Small(
        "Small (5x5)", arrayOf(
            arrayOf(1, 1, 1),
            arrayOf(1, 0, 0),
            arrayOf(1, 1, 1)
        )
    ),
    Medium("Medium (100x100)", Array(100) { Array(100) { 1 } }),
    NoPath(
        "NoPath", arrayOf(
            arrayOf(1, 1, 1, 1, 1),
            arrayOf(0, 0, 0, 1, 1),
            arrayOf(0, 0, 0, 1, 1),
            arrayOf(1, 1, 1, 0, 0),
            arrayOf(1, 1, 1, 0, 0)
        )
    ),
    MultiplePath(
        "Multiple path", arrayOf(
            arrayOf(1, 1, 1, 1),
            arrayOf(1, 1, 0, 1),
            arrayOf(1, 1, 1, 1),
            arrayOf(0, 0, 1, 1)
        )
    ),
    Large("Large (500x500)", Array(500) { Array(500) { 1 } }),
    XLarge("XLarge (1000x1000)", Array(1000) { Array(1000) { 1 } })
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val rowSize: Int = 0,
    val columnSize: Int = 0,
    val uiPoints: List<UiPoint> = listOf(),
    val algorithmRanMs: Long = -1L,
    val selectedAlgorithm: Algorithm = Algorithm.BFS,
    val selectedSampleData: SampleData = SampleData.Small
)