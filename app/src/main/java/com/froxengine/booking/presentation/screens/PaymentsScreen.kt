package com.froxengine.booking.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
    fun PaymentMethodsScreen(
    navigateCardPayment: () -> Unit,
    order: StateFlow<Order>,
    clientName: String,
    navigateBack: () -> Unit,
    navigateToQrPayment: () -> Unit
) {
    var selectedOption by remember { mutableStateOf<String?>(null) } // Estado para saber qué opción fue seleccionada
    val orderState by order.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        // Primer contenedor: 25% del espacio
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
                .background(
                    color = Color(0xFFD1A7F7),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                ) // Fondo morado claro con esquinas redondeadas
                .border(2.dp, Color.Transparent, RoundedCornerShape(8.dp))
        ) {
            // Los tres textos dentro del contenedor
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Métodos de pago",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 24.sp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = clientName,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Total: S/${String.format("%.2f", orderState.total.toDouble())}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Segundo contenedor: 40% del espacio
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f) // 40% del espacio total
                .padding(bottom = 16.dp) // Padding para separar del siguiente elemento
                .fillMaxHeight() // Asegura que el Box ocupe todo el espacio disponible en altura
                .wrapContentSize(Alignment.Center) // Centra todo el contenido del Box
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Opción de Código QR
                Card(
                    modifier = Modifier
                        .clickable {
                            selectedOption = "QR"
                            navigateToQrPayment()
                        }
                        .padding(8.dp)
                        .weight(1f), // Ocupa el mismo espacio
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Sombra del Card
                    colors = CardDefaults.cardColors(containerColor = Color.White), // Fondo blanco
                    border = if (selectedOption == "QR") BorderStroke(2.dp, Color.Black) else null // Borde negro si está seleccionado
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Código QR",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.codqr),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.billetera),
                            contentDescription = null,
                            modifier = Modifier
                                .height(100.dp) // Define la altura, si lo deseas
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Opción de Pago con Tarjeta
                Card(
                    modifier = Modifier
                        .clickable { navigateCardPayment() }
                        .padding(8.dp)
                        .weight(1f), // Ocupa el mismo espacio
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Sombra del Card
                    colors = CardDefaults.cardColors(containerColor = Color.White), // Fondo blanco
                    border = if (selectedOption == "Card") BorderStroke(2.dp, Color.Black) else null // Borde negro si está seleccionado
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Pago con tarjeta",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.tarjetafac),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.tarjetapago),
                            contentDescription = null,
                            modifier = Modifier
                                .height(100.dp) // Define la altura, si lo deseas
                        )
                    }
                }
            }
        }

        // Tercer contenedor: 25% del espacio (Botón)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f) // 25% del espacio total
                .fillMaxHeight() // Asegura que el Box ocupe todo el espacio disponible en altura
                .background(
                    color = Color(0xFFADEAAD), // Fondo verde esperanza
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    ) // Solo las esquinas superiores redondeadas
                )
                .wrapContentSize(Alignment.Center) // Centra todo el contenido del Box
        ) {
            Button(
                onClick = navigateBack,
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray), // Fondo gris
                border = BorderStroke(2.dp, Color.Black), // Borde negro
                shape = RoundedCornerShape(16.dp), // Esquinas redondeadas, ajusta según sea necesario
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp) // Elevación (opcional)
            ) {
                Text("CANCELAR", color = Color.Black) // Texto negro
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentMethodsScreen() {
    val fakeOrder = Order(
        sportCenterSelected = "Example Sport Center",
        timeSelected = "10:00 AM - 11:00 AM",
        timeSlots = emptyList(),
        total = "20"
    )
    PaymentMethodsScreen(
        navigateCardPayment = {},
        MutableStateFlow(fakeOrder),
        "Angles Payano, Neil",
        navigateBack = {  }) {
    }
}