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
            val grid = getSampleData(sampleData)
            val algorithm = getAlgorithm(selectedAlgorithm)
            val uiPoints = mutableListOf<UiPoint>()
            withContext(Dispatchers.Default) {
                val shortestPath = algorithm.execute(grid)
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
                uiPoints = uiPoints
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

    private fun getSampleData(sampleData: SampleData): Array<Array<Int>> {
        return when (sampleData) {
            SampleData.Small -> arrayOf(
                arrayOf(1, 1, 1),
                arrayOf(1, 0, 0),
                arrayOf(1, 1, 1)
            )

            SampleData.Large -> Array(100) { Array(100) { 1 } }
            SampleData.NoPath -> arrayOf(
                arrayOf(1, 1, 1, 1, 1),
                arrayOf(0, 0, 0, 1, 1),
                arrayOf(0, 0, 0, 1, 1),
                arrayOf(1, 1, 1, 0, 0),
                arrayOf(1, 1, 1, 0, 0)
            )

            SampleData.MultiplePath -> arrayOf(
                arrayOf(1, 1, 1, 1),
                arrayOf(1, 1, 0, 1),
                arrayOf(1, 1, 1, 1),
                arrayOf(0, 0, 1, 1)
            )
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