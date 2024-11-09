package com.froxengine.booking.presentation.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.froxengine.booking.R
import com.froxengine.booking.data.model.Order
import com.froxengine.booking.presentation.components.LoadingAnimation
import com.froxengine.booking.presentation.components.SuccessDialog

@Composable
fun PaymentQrScreen(goHome: ()-> Unit, orderState: Order, clientName: String, navigateBack: ()-> Unit, ) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Primer Box (65% del espacio)
        Box(
            modifier = Modifier
                .weight(0.65f)
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Billeteras digitales",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 24.sp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Ingresa tu aplicación",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(2.dp))

                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = R.drawable.imagen01),
                    contentDescription = "Imagen de la aplicación"
                )
                Text(
                    text = "y escanea este código QR",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))
                var showDialog: Boolean by remember { mutableStateOf(false) }

                if (showDialog) {
                    SuccessDialog(clientName = clientName, orderState = orderState) {
                        showDialog = false
                        goHome()
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.codigoqr),
                    contentDescription = "Código QR",
                    modifier = Modifier
                        .size(200.dp)
                        .clickable {
                            Log.v("Booking", "click en qr")
                            showDialog = true
                        }
                )


                Spacer(modifier = Modifier.height(16.dp))


//                Image(
//                    painter = painterResource(id = R.drawable.load),
//                    contentDescription = "Cargando",
//                    modifier = Modifier
//                        .size(60.dp)
//                        .padding(8.dp)
//                )
            }
        }

        // Segundo Box (35% del espacio)
        Box(
            modifier = Modifier
                .weight(0.35f)
                .fillMaxHeight()
                .background(
                    color = Color(0xFFADEAAD),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
        ) {
            LoadingAnimation(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopCenter) // Centra la animación horizontalmente y la coloca en la parte superior
                    .offset(y = (-20).dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = clientName,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Total: S/${String.format("%.2f", orderState.total.toDouble())}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = navigateBack,
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    border = BorderStroke(2.dp, Color.Black),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text("CANCELAR", color = Color.Black)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentQrScreenPreview() {
    val fakeOrder = Order(
        sportCenterSelected = "Example Sport Center",
        timeSelected = "10:00 AM - 11:00 AM",
        timeSlots = emptyList(),
        total = "20"
    )
    PaymentQrScreen({}, fakeOrder, "Angles Payano, Neil") {}
}