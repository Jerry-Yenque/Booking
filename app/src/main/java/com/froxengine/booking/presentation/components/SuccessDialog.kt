package com.froxengine.booking.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.froxengine.booking.data.model.Order
import com.froxengine.booking.presentation.ui.theme.BooKingTheme

@Composable
fun SuccessDialog(clientName: String, orderState: Order, onConfirm: ()-> Unit) {
    AlertDialog(onDismissRequest = { /*TODO*/ }, text = {
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Resevación Exitosa", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Presentarse con su documento de identidad la fecha elegida", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = clientName, fontSize = 22.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = orderState.timeSelected, fontSize = 20.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text= "Total: S/${String.format("%.2f", orderState.total.toDouble())}", fontSize = 20.sp, fontWeight = FontWeight.Medium)

        }

    }, confirmButton = {
        TextButton(
            onClick = {
                onConfirm()
            }
        ) {
            Text("Aceptar"  )
        }
    })
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EndScreen(goNuevaVenta: () -> Unit) {

    Scaffold(
        floatingActionButton = {
            Row(modifier = Modifier
                .offset(y = 14.dp)
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center) {
                Column(Modifier.align(Alignment.CenterVertically)) {
//                    FloatingActionButton(
//                        onClick = { },
//                        containerColor = Color(0xFF00809A)
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.),
//                            contentDescription = "printer icon",
//                            tint = Color.White
//                        )
//                    }
                    Text(
                        text = "Imprimir", // Descripción para el primer botón flotante
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp)) // Espacio entre los botones
                Column {
//                    FloatingActionButton(
//                        onClick = { goNuevaVenta() },
//                        modifier = Modifier.align(Alignment.CenterHorizontally), // Centra el botón flotante horizontalmente
//                        containerColor = Color(0xFF00809A)
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_file),
//                            contentDescription = "new sell",
//                            tint = Color.White,
//                            modifier = Modifier.size(40.dp)
//                        )
//                    }
                    Text(
                        text = "Nueva venta", // Descripción para el primer botón flotante
                        color = Color.Gray,
                        fontSize = 12.sp, // Ajusta el tamaño del texto según sea necesario
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier // Centra el texto horizontalmente
                            .padding(top = 4.dp)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Image(
//                    modifier = Modifier.height(90.dp),
//                    painter = painterResource(id = R.drawable.synerpos_logo),
//                    contentDescription = "syner logo"
//                )
                Text(text = "Transacción Exitosa!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "payable.number", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    item {
//                        OrderDetails(payable)
                        Spacer(modifier = Modifier.height(16.dp))
//                        SellDetails(sellDetail)
                    }
                    item {
                        Spacer(modifier = Modifier.height(84.dp)) // Ajusta la altura según sea necesario
                    }
                }
            }
        },
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun OrderDetails() {
    val igv = "IGV"
    Column(modifier = Modifier.fillMaxWidth()) {
//        payable.items.forEach {
//            PayableItemRow(it)
//        }
        HorizontalDivider()
        OrderItem(description = "Neto:", price = "payable.netTotal.toString()", isTotal = true)
        OrderItem(description = "I.G.V:", price = igv.toString(), isTotal = true)
//        OrderItem(description = "Other hidden fee:", price = "5.00", isTotal = true)
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
        OrderItem(
            description = "Total:",
            price = String.format("%,.2f", "20.00"),
            isTotal = true,
            isBold = true
        )
    }
}

@Composable
fun OrderItem(
    description: String,
    price: String,
    isTotal: Boolean = false,
    isBold: Boolean = false
) {
    val fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
    val color = if (isTotal) Color.Gray else Color.Black
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = description, color = color, fontWeight = fontWeight)
        Text(text = "S/$price", color = color, fontWeight = fontWeight)
    }
}

@Preview
@Composable
fun EndScreenPreview() {
    BooKingTheme {
        SuccessDialog(clientName = "Yenque Calero, Jerry Aldair", orderState = Order(
            sportCenterSelected = "Example Sport Center",
            timeSelected = "10:00 AM - 11:00 AM",
            timeSlots = emptyList(),
            total = "20"
        )) {

        }
    }
}