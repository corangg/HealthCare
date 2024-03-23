package com.example.healthcare.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.healthcare.GraghColor


@Composable
fun DrawLineGraph(list : List<Float>, graghColor: GraghColor = GraghColor(),exerciseInfoNum: Int) {

    val scrollState = rememberScrollState()
    val density = LocalDensity.current
    val recordDate = "2024년 3월 22일"
    val exerciseInfoList = listOf("무게", "세트", "횟수")
    val exerciseUnitList = listOf("kg", "세트", "회")
    val stepX = 100.dp
    val selectedValueIndex = remember { mutableStateOf(0) }

    LaunchedEffect(scrollState.value) {
        with(density) {
            val currentIndex = (scrollState.value / stepX.toPx()).toInt()
            selectedValueIndex.value = currentIndex.coerceIn(list.indices)
        }
    }


    val value : Float = list.getOrNull(selectedValueIndex.value) ?: 0f
    Column {
        Text(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(16.dp),
            text = recordDate,
            style = TextStyle(color = Color.White, textAlign = TextAlign.Center, fontSize = 16.sp)
        )

        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .background(Color.Black, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .padding(16.dp),
            text = exerciseInfoList[exerciseInfoNum] + " : " + value.toString() + " " + exerciseUnitList[exerciseInfoNum],
            style = TextStyle(color = Color.White, textAlign = TextAlign.Center, fontSize = 16.sp)
        )

        BoxWithConstraints { // 화면 크기를 얻기 위해 BoxWithConstraints 사용
            val screenWidth = maxWidth // 현재 화면의 너비
            val stepX = with(LocalDensity.current) { 100.dp.toPx() }
            val halfScreenWidth = screenWidth / 2 // 화면 너비의 절반
            Box(modifier = Modifier.background(Color.Black, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))){
                Row(modifier = Modifier.horizontalScroll(scrollState)) {
                    val canvasWidth = 100.dp * (list.size - 1)
                    Spacer(modifier = Modifier.width(halfScreenWidth - 16.dp))
                    Canvas(
                        modifier = Modifier
                            .height(160.dp)
                            .width(canvasWidth)
                            .padding(horizontal = 16.dp)
                            .padding(top = 20.dp),
                        onDraw = {
                            val maxYValue = list.maxOrNull() ?: 0f
                            val stepX = 100.dp.toPx() // 포인트 간격을 100dp로 고정
                            val stepY = size.height / maxYValue

                            list.forEachIndexed { index, y ->
                                val x = index * stepX
                                val yPos = size.height - (y / maxYValue) * size.height
                                drawCircle(
                                    color = graghColor.pointColor,
                                    radius = 4.dp.toPx(),
                                    center = Offset(x, yPos)
                                )
                                if (index < list.size - 1) {
                                    val nextY = list[index + 1]
                                    val nextX = (index + 1) * stepX
                                    val nextYPos = size.height - (nextY / maxYValue) * size.height
                                    drawLine(
                                        start = Offset(x, yPos),
                                        end = Offset(nextX, nextYPos),
                                        color = graghColor.lineColor,
                                        strokeWidth = 2f
                                    )
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(halfScreenWidth + 18.dp))
                }
            }
        }

        Canvas(
            modifier = Modifier.fillMaxWidth()
            ,onDraw = {drawLine(
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                color = graghColor.indicatorLineColor,
                strokeWidth = 2f)

                val trianglePath = Path().apply {
                    moveTo(x = size.width / 2, size.height - 16.dp.toPx())
                    lineTo(x = size.width / 2 - 10.dp.toPx(), size.height)
                    lineTo(x = size.width / 2 + 10.dp.toPx(), size.height)
                    close()
                }

                drawPath(
                    path = trianglePath,
                    color = graghColor.indicatorColor
                )
            })
    }

}


@Preview
@Composable
fun PreviewSimpleLineChart() {
    val dataPoints = listOf(50f, 100f, 150f, 200f, 150f, 202f, 194f, 80f, 100f, 150f, 200f, 150f, 202f, 194f, 80f) // 데이터 포인트
    DrawLineGraph(dataPoints, exerciseInfoNum = 0)

}