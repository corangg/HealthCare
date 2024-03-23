package com.example.healthcare.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.ExerciseType
import com.example.healthcare.GraphColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun DrawLineGraph(
    infoList: List<Float>,
    dateList : List<String>,
    graphColor: GraphColor = GraphColor(),
    exerciseInfoNum: Int,
    type: Int = 0) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val stepX = with(density) { 100.dp.toPx() }
    var exerciseInfoList = listOf("")
    var exerciseUnitList = listOf("")
    when(type){
        0->{
            exerciseInfoList = listOf("무게", "세트", "횟수")
            exerciseUnitList = listOf("kg", "세트", "회")
        }
        1->{
            exerciseInfoList = listOf("인클라인", "시간", "거리")
            exerciseUnitList = listOf("º", "분", "km")
        }
    }


    val nearestPointIndex = remember { mutableStateOf(0) }
    val scrollToPosition = remember { mutableStateOf(0f) }

    LaunchedEffect(scrollState.value) {
        coroutineScope.launch {
            delay(50)
            val currentIndex = (scrollState.value / stepX).roundToInt().coerceIn(infoList.indices)
            nearestPointIndex.value = currentIndex
            scrollToPosition.value = (currentIndex * stepX)
            scrollState.animateScrollTo(scrollToPosition.value.toInt())
        }
    }

    val value = infoList.getOrNull(nearestPointIndex.value)?.toInt() ?: 0f
    val date = dateList.getOrNull(nearestPointIndex.value) ?: ""

    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(16.dp),
            text = date,
            style = TextStyle(color = Color.White, textAlign = TextAlign.Center, fontSize = 16.sp)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .background(Color.Black, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(16.dp),
            text = "${exerciseInfoList[exerciseInfoNum]} : $value ${exerciseUnitList[exerciseInfoNum]}",
            //text = "${option[exerciseInfoNum]} : $value ${unit[exerciseInfoNum]}",
            style = TextStyle(color = Color.White, textAlign = TextAlign.Center, fontSize = 16.sp)
        )

        BoxWithConstraints {
            val screenWidth = maxWidth
            val halfScreenWidth = screenWidth / 2
            Box(modifier = Modifier.background(Color.Black, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))) {
                Row(modifier = Modifier.horizontalScroll(scrollState)) {
                    val canvasWidth = 100.dp * (infoList.size - 1)
                    Spacer(modifier = Modifier.width(halfScreenWidth - 16.dp))
                    Canvas(
                        modifier = Modifier
                            .height(160.dp)
                            .width(canvasWidth)
                            .padding(horizontal = 16.dp)
                            .padding(top = 20.dp),
                        onDraw = {
                            val maxYValue = infoList.maxOrNull() ?: 0f
                            val stepY = size.height / maxYValue

                            infoList.forEachIndexed { index, y ->
                                val x = index * stepX
                                val yPos = size.height - (y / maxYValue) * size.height
                                drawCircle(
                                    color = graphColor.pointColor,
                                    radius = 4.dp.toPx(),
                                    center = Offset(x, yPos)
                                )
                                if (index < infoList.size - 1) {
                                    val nextY = infoList[index + 1]
                                    val nextX = (index + 1) * stepX
                                    val nextYPos = size.height - (nextY / maxYValue) * size.height
                                    drawLine(
                                        start = Offset(x, yPos),
                                        end = Offset(nextX, nextYPos),
                                        color = graphColor.lineColor,
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
            modifier = Modifier.fillMaxWidth(),
            onDraw = {
                drawLine(
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    color = graphColor.indicatorLineColor,
                    strokeWidth = 2f
                )

                val trianglePath = Path().apply {
                    moveTo(x = size.width / 2, size.height - 16.dp.toPx())
                    lineTo(x = size.width / 2 - 10.dp.toPx(), size.height)
                    lineTo(x = size.width / 2 + 10.dp.toPx(), size.height)
                    close()
                }

                drawPath(
                    path = trianglePath,
                    color = graphColor.indicatorColor
                )
            }
        )
    }
}

@Preview
@Composable
fun PreviewSimpleLineChart() {
    val dataPoints = listOf(50f, 100f, 150f, 200f, 150f, 202f, 194f, 80f, 100f, 150f, 200f, 150f, 202f, 194f, 80f) // 데이터 포인트
    val dateList = listOf("2020","2027","2028","2021","2022","2023","2024",) // 데이터 포인트
    DrawLineGraph(dataPoints, exerciseInfoNum = 0, dateList = dateList, type = 0)
}