package com.froxengine.booking.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TimerScreen( modifier: Modifier = Modifier, navController: () -> Unit, initialTime: Int = 10) {
    // Estado para manejar el tiempo restante
    var timeLeft by remember { mutableStateOf(initialTime) }

    // Inicia el cronómetro al entrar en la pantalla
    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L) // Espera un segundo
            timeLeft -= 1
        } else {
            // Navegar a otra pantalla cuando el temporizador llegue a cero
            navController()
        }
    }

    // UI del cronómetro
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Dibujar un círculo con el tiempo dentro
        CircularTimerDisplay(timeLeft = timeLeft, initialTime = initialTime, modifier = modifier)
    }
}

@Composable
fun CircularTimerDisplay(timeLeft: Int, initialTime: Int, modifier: Modifier = Modifier) {
    // Definimos el progreso del temporizador (de 1.0 a 0.0)
    val progress = timeLeft.toFloat() / initialTime.toFloat()

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        // Dibujar el círculo usando Canvas
        Canvas(modifier = modifier) {
            drawArc(
                color = Color.Blue,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = 3.dp.toPx())
            )
        }

        // Mostrar el tiempo restante en el centro del círculo
        Text(
            text = "$timeLeft s",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewTimer() {
    TimerScreen(initialTime = 120, modifier = Modifier.size(40.dp), navController = { /*TODO*/ })
}