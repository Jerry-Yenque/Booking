package com.froxengine.booking.presentation.screens.payments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.froxengine.booking.presentation.ui.theme.BooKingTheme

@Composable
fun CardPayment(total: String) {
    var cvc by remember { mutableStateOf("") }
    var card by remember { mutableStateOf("") }
    var titularName by remember {
        mutableStateOf("")
    }
//    var expirationDate by remember {
//        mutableStateOf("")
//    }

    var expirationDate by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = Modifier.padding(16.dp)) {

        // Payment Method Selection
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Pago con tarjeta")
        }

        // Credit Card Details
        Column(modifier = Modifier.padding(top = 16.dp)) {
            TextField(
                value = card,
                onValueChange = { card = it },
                label = { Text("Número de tarjeta") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = titularName,
                onValueChange = { titularName = it },
                label = { Text("Nombre del titular") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = expirationDate,
                onValueChange = { newValue ->
                    // Filtrar solo números
                    val digitsOnly = newValue.text.filter { it.isDigit() }

                    // Limitar la longitud a un máximo de 6 dígitos (2 para el mes y 4 para el año)
                    val limitedDigits = if (digitsOnly.length > 6) digitsOnly.substring(0, 6) else digitsOnly

                    // Construir el nuevo valor con el formato "MM/AAAA"
                    val formattedValue = when {
                        limitedDigits.length <= 2 -> limitedDigits // Solo mes (hasta 2 dígitos)
                        else -> "${limitedDigits.substring(0, 2)}/${limitedDigits.substring(2)}" // Mes y año
                    }

                    // Crear el nuevo estado de TextFieldValue manteniendo la posición del cursor al final
                    expirationDate = newValue.copy(
                        text = formattedValue,
                        selection = TextRange(formattedValue.length)
                    )
                },
                label = { Text("MM / YYYY") },
                placeholder = { Text("MM / YYYY") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = cvc,
                onValueChange = {cvc = it},
                label = { Text("CVC") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Billing Address
        Column(modifier = Modifier.padding(top = 16.dp)) {
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Código postal") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(80.dp))

        // Price Summary
        Column(modifier = Modifier.padding(top = 16.dp)) {
//            Text(text = "Price: \$144")
//            Text(text = "Discount: -\$24")
            Text(textAlign = TextAlign.Center, text = "Total: S/${total}", modifier = Modifier.fillMaxWidth())
        }

        // Upgrade Button
        Button(
            onClick = { /* Implement upgrade logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Cancelar")
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun CardPaymentPreview() {
    BooKingTheme {
        CardPayment(total = "130")
    }
}
