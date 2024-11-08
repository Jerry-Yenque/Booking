package com.froxengine.booking.presentation.screens.client
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.froxengine.booking.data.model.IdentificationType
import com.froxengine.booking.data.model.Order
import com.froxengine.booking.presentation.components.LoadingAnimation
import com.froxengine.booking.presentation.ui.theme.BooKingTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterClientScreen(
    navigateToPayments: ()-> Unit,
    order: StateFlow<Order>,
    clientName: String,
    isLoading: Boolean,
    search: (String, IdentificationType) -> Unit,
) {
    val orderStatus by order.collectAsState()
    var selectedDocumentType by remember { mutableStateOf("Tipo de documento") }
    var documentNumber by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "CENTRO DEPORTIVO 1\nRESERVA",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Dropdown for document type (Tipo de documento) with arrow
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedDocumentType,
                onValueChange = {},
                readOnly = true, // To prevent user input
                label = { Text("Tipo de documento") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor() // Important for aligning the dropdown
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("DNI") },
                    onClick = {
                        selectedDocumentType = "DNI"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("RUC") },
                    onClick = {
                        selectedDocumentType = "RUC"
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        var maxDigits: Int by remember {
            mutableStateOf(0)
        }

        // Input for document number (Número de documento)
        TextField(
            supportingText = {
                if (selectedDocumentType == "DNI") {
                    Text(text = "Ingrese 8 dígitos")
                    maxDigits = 8
                }
                if (selectedDocumentType == "RUC") {
                    Text(text = "Ingrese 11 dígitos")
                    maxDigits = 11
                }

            },
            isError = (documentNumber.length in 1..<maxDigits),
            enabled = selectedDocumentType != "Tipo de documento",
            singleLine = true,
            value = documentNumber,
            onValueChange = { input ->
                val sanitizedInput = input.filter { it.isDigit() }
                if (sanitizedInput.length <= maxDigits) {
                    documentNumber = sanitizedInput

                    if (sanitizedInput.length == maxDigits) {
                        when (selectedDocumentType) {
                            "DNI" -> {
                                search(documentNumber, IdentificationType.DNI)
                            }
                            "RUC" -> {
                                search(documentNumber, IdentificationType.RUC)
                            }
                        }
                    }
                }
                            },
            label = { Text("Número de documento") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

//        Spacer(modifier = Modifier.height(32.dp))

        Box(modifier = Modifier.height(150.dp)) {
            if (isLoading) {

                LoadingAnimation(Modifier.size(150.dp))
            }
            if (!isLoading && clientName.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = clientName,
                    fontSize = 34.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        // Display of time slots and other elements
        Text(text = "Cancha de 50m2 de gras sintético", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Hora", fontSize = 16.sp, modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "10:00 AM - 11:00 AM", fontSize = 14.sp)
            Text(text = "12:00 AM - 1:00 PM", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Date and payment button
        Text(text = orderStatus.timeSelected, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            enabled = clientName.isNotEmpty(),
            onClick = navigateToPayments) {
            Text("PROCEDER AL PAGO")
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(text = "Total: S/${String.format("%.2f", orderStatus.total.toDouble())}", fontSize = 18.sp)
    }
}



@Preview(showSystemUi = true)
@Composable
fun RegisterClientPreview() {
    BooKingTheme {
        val fakeOrder = Order(
            sportCenterSelected = "Example Sport Center",
            timeSelected = "10:00 AM - 11:00 AM",
            timeSlots = emptyList(),
            total = "20"
        )
        RegisterClientScreen(
            {},
            MutableStateFlow(fakeOrder),
            "" ,
            false
        ) { _, _ -> }
    }
}