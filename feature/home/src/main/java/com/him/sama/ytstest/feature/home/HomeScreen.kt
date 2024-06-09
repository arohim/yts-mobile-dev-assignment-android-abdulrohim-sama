package com.him.sama.ytstest.feature.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.him.sama.ytstest.common.ui.theme.MyJetpackComposeTheme
import com.him.sama.ytstest.feature.home.model.Algorithm
import com.him.sama.ytstest.feature.home.model.HomeUiState
import com.him.sama.ytstest.feature.home.model.SampleData
import com.him.sama.ytstest.feature.home.model.UiPoint

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle(HomeUiState()).value

    Body(
        uiState,
        onSelectAlgorithm = viewModel::onSelectAlgorithm,
        onSelectSampleData = viewModel::onSelectSampleData
    )
}

@Composable
private fun Body(
    uiState: HomeUiState,
    onSelectAlgorithm: (Algorithm) -> Unit,
    onSelectSampleData: (SampleData) -> Unit,
) {
    val row = uiState.rowSize
    val column = uiState.columnSize
    val defaultLineColor = Color.Black
    val algorithms = Algorithm.entries.toTypedArray()
    val sampleData = SampleData.entries.toTypedArray()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AlgorithmDropdown(uiState.selectedAlgorithm, algorithms, onSelect = onSelectAlgorithm)
        Spacer(modifier = Modifier.height(8.dp))
        SampleDataDropDown(uiState.selectedSampleData, sampleData, onSelect = onSelectSampleData)
        Spacer(modifier = Modifier.height(16.dp))
        if (uiState.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Processing...")
            }
        } else {
            Column(
                modifier = Modifier,
            ) {
                Guide()
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.65f),
                    verticalArrangement = Arrangement.Top,
                ) {
                    DrawTable(row, column, uiState, defaultLineColor)
                }
            }
        }
    }
}

@Composable
private fun Guide() {
    Row(
        modifier = Modifier,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(10.dp)
                .background(Color.Red)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Red color is visited",
            color = Color.Black
        )
    }
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(Color.Black)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Black (0) can walk through",
            color = Color.Black
        )
    }
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(Color.White)
                .border(1.dp, Color.Black)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Black not visiting point",
            color = Color.Black
        )
    }
    Row(
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(10.dp)
                .background(Color.Blue)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Blue is the shortest path",
            color = Color.Black
        )
    }
}

@Composable
private fun DrawTable(
    row: Int,
    column: Int,
    uiState: HomeUiState,
    defaultLineColor: Color
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val width = size.width
        val height = size.height

        val rowSize = width / row
        val columnSize = height / column

        uiState.uiPoints.forEach {
            val color = if (it.value == 0) {
                Color.Black
            } else if (it.isPath) {
                Color.Blue
            } else if (it.isVisited) {
                Color.Red
            } else {
                defaultLineColor
            }
            val style = if (it.value == 0 || it.isVisited) {
                Fill
            } else {
                Stroke()
            }
            val x = it.y * rowSize
            val y = it.x * columnSize
            drawRect(
                color = color,
                topLeft = Offset(x, y),
                size = Size(width = rowSize, height = columnSize),
                style = style
            )
//            if (style == Fill) {
//                drawRect(
//                    color = defaultLineColor,
//                    topLeft = Offset(x, y),
//                    size = Size(width = rowSize, height = columnSize),
//                    style = Stroke()
//                )
//            }
//            here to debug x y displaying in the correct position or not
//            drawContext.canvas.nativeCanvas.apply {
//                drawText(
//                    "x=${it.x},y=${it.y}",
//                    x,
//                    y,
//                    android.graphics.Paint().apply {
//                        this.color = android.graphics.Color.BLACK
//                        textSize = 20.sp.toPx()
//                    }
//                )
//            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AlgorithmDropdown(
    selected: Algorithm,
    list: Array<Algorithm>,
    onSelect: (Algorithm) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Algorithm",
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = selected.algoName,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    list.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = item.algoName,
                                    color = Color.Black
                                )
                            },
                            onClick = {
                                if (item == Algorithm.BFS) {
                                    onSelect(item)
                                }
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SampleDataDropDown(
    selected: SampleData,
    list: Array<SampleData>,
    onSelect: (SampleData) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Sample data", style = MaterialTheme.typography.labelLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = selected.dataName,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    list.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = item.dataName,
                                    color = Color.Black
                                )
                            },
                            onClick = {
                                onSelect(item)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

val testUiState = HomeUiState(
    rowSize = 4,
    columnSize = 4,
    uiPoints = listOf(
        UiPoint(x = 0, y = 0, value = 1, isVisited = true, isPath = true),
        UiPoint(x = 0, y = 1, value = 1, isVisited = false, isPath = false),
        UiPoint(x = 0, y = 2, value = 0, isVisited = false, isPath = false),
        UiPoint(x = 0, y = 3, value = 0, isVisited = false, isPath = false),
        UiPoint(x = 1, y = 0, value = 1, isVisited = true, isPath = true),
        UiPoint(x = 1, y = 1, value = 1, isVisited = true, isPath = false),
        UiPoint(x = 1, y = 2, value = 0, isVisited = false, isPath = false),
        UiPoint(x = 1, y = 3, value = 1, isVisited = false, isPath = false),
        UiPoint(x = 2, y = 0, value = 1, isVisited = true, isPath = true),
        UiPoint(x = 2, y = 1, value = 1, isVisited = true, isPath = false),
        UiPoint(x = 2, y = 2, value = 1, isVisited = true, isPath = false),
        UiPoint(x = 2, y = 3, value = 0, isVisited = true, isPath = false),
        UiPoint(x = 3, y = 0, value = 1, isVisited = true, isPath = true),
        UiPoint(x = 3, y = 1, value = 1, isVisited = true, isPath = true),
        UiPoint(x = 3, y = 2, value = 1, isVisited = true, isPath = true),
        UiPoint(x = 3, y = 3, value = 1, isVisited = true, isPath = true)
    )
)

@Preview
@Composable
fun PreviewBody() {
    MyJetpackComposeTheme {
        Body(testUiState, {}, {})
    }
}
