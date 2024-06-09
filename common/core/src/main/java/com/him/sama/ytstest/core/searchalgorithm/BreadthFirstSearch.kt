package com.him.sama.ytstest.core.searchalgorithm

import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject

/**
 * Time complexity: O(n * m)
 *    where n * m is the size of the grid
 *     log (n * m) is time complexity of add/poll operations for priority queue
 *
 * Space complexity: O(n * m)
 *   uses a visited and queue, both of which can store up to O(n * m) elements
 */
class BreadthFirstSearch @Inject constructor() : ShortestPathFinder {

    override lateinit var visited: Array<BooleanArray>
    private lateinit var parent: Array<Array<Point?>>

    override fun execute(grid: Array<Array<Int>>): List<Point> {
        // Return -1 if grid is empty
        if (grid.isEmpty() || grid[0].isEmpty())
            return emptyList()

        val rows = grid.size
        val cols = grid[0].size
        visited = Array(rows) { BooleanArray(cols) }
        parent = Array(rows) { arrayOfNulls(cols) }

        // Return -1 if it reached the destination
        if (grid[0][0] == 0 || grid[rows - 1][cols - 1] == 0)
            return emptyList()

        // Define which way we will explore in this case we can go right, down, left and right
        val directions = listOf(Point(1, 0), Point(0, 1), Point(-1, 0), Point(0, -1))

        // Create queue to store the point that we will explore
        val queue: Queue<Point> = LinkedList()

        // Starting point
        queue.add(Point(0, 0))

        // Mark the staring point as a visited point
        visited[0][0] = true

        // Also count the starting point as 1 step
        var steps = 0

        // Loop until queue is empty
        //    when queue is empty means we have visited all the possible path
        while (queue.isNotEmpty()) {
            repeat(queue.size) {
                // New starting point to explore
                val point = queue.poll()

                // Return steps when we've reached the destination
                if (point.x == rows - 1 && point.y == cols - 1) {
                    return constructShortestPath(point)
                }

                // Start exploring using pre-defined directions
                for (dir in directions) {
                    val newX = point.x + dir.x
                    val newY = point.y + dir.y

                    // Check if
                    // - newX is in boundary
                    // - and newY is in boundary
                    // - and the new point haven't visited
                    // - and the new point is 1
                    if (newX in 0 until rows
                        && newY in 0 until cols
                        && !visited[newX][newY]
                        && grid[newX][newY] == 1
                    ) {
                        // add the new point into queue to be next point to explore
                        queue.add(Point(newX, newY))

                        // mark the new point to be visited
                        visited[newX][newY] = true

                        // save predecessor to track back the path
                        parent[newX][newY] = point
                    }
                }
            }
            // Increase step
            steps++
        }
        return emptyList()
    }

    private fun constructShortestPath(destination: Point): List<Point> {
        val path = mutableListOf<Point>()
        var current: Point? = destination
        while (current != null) {
            path.add(current)
            current = parent.getOrNull(current.x)?.getOrNull(current.y)
        }
        return path.reversed()
    }

}