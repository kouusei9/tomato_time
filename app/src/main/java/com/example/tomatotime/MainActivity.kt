package com.example.tomatotime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
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
import kotlin.math.cos
import kotlin.math.sin


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TomatotimeTheme {
                val timeViewModel: TimeViewModel = viewModel()
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
                TomatoLayout(timeViewModel)
//                }
            }
        }
    }
}

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TomatoLayout(timeViewModel: TimeViewModel) {
    Scaffold(
        Modifier.fillMaxSize()
    )
    { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DrawCircle(timeViewModel.timeAngle.value)

            Text(
                text = timeViewModel.timeFormat.value,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 20.dp)
            )

            DrawButtons(
                timeViewModel.startDountDown.value,
                onStartCountdown = { timeViewModel.startCountDown() },
                onStopCountdown = { timeViewModel.stopCountDown() },
                onReset = { timeViewModel.reset() }
            )
        }
    }
}


@Composable
fun DrawButtons(
    statCountDown: Boolean,
    onStartCountdown: () -> Unit,
    onStopCountdown: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (statCountDown) {
                    onStopCountdown()
                } else {
                    onStartCountdown()
                }
            }) {
            if (statCountDown) {
                Text(text = stringResource(id = R.string.stop))
            } else {
                Text(text = stringResource(id = R.string.start))
            }
        }
        if (statCountDown) {
            Button(onClick = { onReset() }) {
                Text(text = stringResource(id = R.string.reset))
            }
        }
    }
}

@Composable
fun DrawCircle(sweepAngle: Double) {
    val size = 160.dp
    Box(
        contentAlignment = Alignment.Center,
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    var viewModel: TimeViewModel = TimeViewModel()
    viewModel.timeFormat.value = "30"
    TomatoLayout(viewModel)
}