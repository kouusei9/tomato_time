package com.example.tomatotime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tomatotime.ui.theme.TomatotimeTheme
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TomatotimeTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
                TomatoLayout()
//                }
            }
        }
    }
}

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TomatoLayout() {
    val timeViewModel: TimeViewModel = viewModel()

//    var timeLeft: Long by remember { mutableStateOf(60) }
//    var startCountDown by remember { mutableStateOf(false) }

    Scaffold(
        Modifier.fillMaxSize()
    )
    { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DrawCircle(timeViewModel.timeAngle.value)
            DrawButtons(
                timeViewModel.startDountDown.value,
                timeLeft = timeViewModel.timeLeft.value,
                updateTimeLeft = {  },
                onStartCountdown = { timeViewModel.startCountDown() }
            )
        }
    }
}


@Composable
fun DrawButtons(
    statCountDown: Boolean,
    timeLeft: Long,
    updateTimeLeft: (Long) -> Unit,
    onStartCountdown: () -> Unit
) {
    Column {
        Button(
            modifier = Modifier
                .width(150.dp)
                .padding(16.dp),
            onClick = { onStartCountdown() }) {
            Text(text = stringResource(id = R.string.start))

            if (statCountDown) {
                // LaunchedEffect用于处理倒计时逻辑
                LaunchedEffect(key1 = timeLeft) {
                    if (timeLeft > 0) {
                        delay(1000) // 等待一秒
                        updateTimeLeft(timeLeft - 1) // 更新剩余时间
                        System.out.println("current time " + timeLeft)
                    }
                }
            }
        }
    }
}

@Composable
fun DrawCircle(sweepAngle: Double) {
    val size = 160.dp
    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val r = size.toPx() / 2
            val strokeWidth = 12.dp.toPx()

            drawCircle(
                color = Color(red = 255, green = 127, blue = 127),
                style = Stroke(
                    width = strokeWidth,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(3.dp.toPx(), 3.dp.toPx())
                    )
                )
            )

            drawArc(
                brush = Brush.sweepGradient(
                    0f to Color.Magenta,
                    0.5f to Color.Blue,
                    0.75f to Color.Green,
                    1f to Color.Magenta
                ),
                startAngle = -90f,
                sweepAngle = sweepAngle.toFloat(),
                useCenter = false,
                style = Stroke(
                    width = strokeWidth
                ),
                alpha = 0.5f
            )

            // draw pointer
            val angle = (360 - sweepAngle) / 180 * Math.PI
            val pointTailLength = 8.dp.toPx()
            // draw pointer line from center to angle.
            drawLine(
                color = Color.Red,
                start = Offset(
                    r + pointTailLength * sin(angle).toFloat(),
                    r + pointTailLength * cos(angle).toFloat()
                ),
                end = Offset(
                    (r - r * sin(angle) - sin(angle) * strokeWidth / 2).toFloat(),
                    (r - r * cos(angle) - cos(angle) * strokeWidth / 2).toFloat()
                ),
                strokeWidth = 2.dp.toPx()
            )
            // pointer small red circle in center
            drawCircle(
                color = Color.Red,
                radius = 5.dp.toPx(),
            )
            // pointer small white circle in center
            drawCircle(
                color = Color.White,
                radius = 3.dp.toPx()
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TomatoLayout()
}