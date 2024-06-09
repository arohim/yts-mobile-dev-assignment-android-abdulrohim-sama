package com.him.sama.ytstest.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.him.sama.ytstest.core.searchalgorithm.BreadthFirstSearch
import com.him.sama.ytstest.core.searchalgorithm.Point
import com.him.sama.ytstest.core.searchalgorithm.ShortestPathFinder
import com.him.sama.ytstest.feature.home.model.Algorithm
import com.him.sama.ytstest.feature.home.model.Algorithm.AStar
import com.him.sama.ytstest.feature.home.model.Algorithm.BFS
import com.him.sama.ytstest.feature.home.model.Algorithm.DFS
import com.him.sama.ytstest.feature.home.model.Algorithm.Djikstra
import com.him.sama.ytstest.feature.home.model.HomeUiState
import com.him.sama.ytstest.feature.home.model.SampleData
import com.him.sama.ytstest.feature.home.model.UiPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.measureTime

@HiltViewModel
class HomeViewModel @Inject constructor(
    val bfs: BreadthFirstSearch
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState = _uiState.asSharedFlow()


    init {
        process(SampleData.Small, BFS)
    }

    private fun process(sampleData: SampleData, selectedAlgorithm: Algorithm) =
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val grid = sampleData.grid
            val algorithm = getAlgorithm(selectedAlgorithm)
            val uiPoints = mutableListOf<UiPoint>()
            var algorithmRanMs: Duration? = null
            withContext(Dispatchers.Default) {
                var shortestPath = listOf<Point>()
                algorithmRanMs = measureTime {
                    shortestPath = algorithm.execute(grid)
                }
                grid.forEachIndexed { rowIndx, row ->
                    row.forEachIndexed { colIndx, col ->
                        uiPoints.add(
                            UiPoint(
                                x = rowIndx,
                                y = colIndx,
                                isVisited = algorithm.visited[rowIndx][colIndx],
                                value = grid[rowIndx][colIndx],
                                isPath = shortestPath.contains(Point(rowIndx, colIndx))
                            )
                        )
                    }
                }
            }
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                rowSize = grid.size,
                columnSize = grid.first().size,
                uiPoints = uiPoints,
                algorithmRanMs = algorithmRanMs?.inWholeMilliseconds ?: -1L
            )
        }

    private fun getAlgorithm(selectedAlgorithm: Algorithm): ShortestPathFinder {
        return when (selectedAlgorithm) {
            BFS -> BreadthFirstSearch()
            DFS -> TODO()
            AStar -> TODO()
            Djikstra -> TODO()
        }
    }

    fun onSelectAlgorithm(algorithm: Algorithm) {
        val sampleData = _uiState.value.selectedSampleData
        _uiState.value = _uiState.value.copy(selectedAlgorithm = algorithm)
        process(sampleData, algorithm)
    }

    fun onSelectSampleData(sampleData: SampleData) {
        val algorithm = _uiState.value.selectedAlgorithm
        _uiState.value = _uiState.value.copy(selectedSampleData = sampleData)
        process(sampleData, algorithm)
    }
}