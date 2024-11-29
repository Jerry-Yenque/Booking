package com.froxengine.booking.presentation.screens.payments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.froxengine.booking.presentation.ui.theme.BooKingTheme

@Composable
fun CardPayment() {
    var selectedPlan by remember { mutableStateOf("Yearly") }
    var discountCode by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf(120) }

    Column(modifier = Modifier.padding(16.dp)) {

        // Payment Method Selection
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Pago con tarjeta")
        }

        // Credit Card Details
        Column(modifier = Modifier.padding(top = 16.dp)) {
            TextField(
                value = "0000 0000 0000 0000",
                onValueChange = {},
                label = { Text("Credit card details") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = "MM / YYYY",
                onValueChange = {},
                label = { Text("MM / YYYY") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = "CVC",
                onValueChange = {},
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

        // Price Summary
        Column(modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Price: \$144")
            Text(text = "Discount: -\$24")
            Text(text = "Total due: \$${totalAmount}")
        }

        // Upgrade Button
        Button(
            onClick = { /* Implement upgrade logic */ },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(text = "Cancelar")
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun CardPaymentPreview() {
    BooKingTheme {
        CardPayment()
    }
}
