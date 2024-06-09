package com.him.sama.ytstest.core.searchalgorithm

/**
 * Use interface to adhere the SOLID concept
 */
interface ShortestPathFinder {
    var visited: Array<BooleanArray>

    fun execute(grid: Array<Array<Int>>): List<Point>
}