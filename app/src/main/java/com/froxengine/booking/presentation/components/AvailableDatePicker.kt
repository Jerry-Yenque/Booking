package com.froxengine.booking.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.froxengine.booking.presentation.screens.detail.getAvailableDates
import com.froxengine.booking.presentation.ui.theme.BooKingTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailableDatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    availableDates: List<LocalDate> = emptyList()
) {
    val context = LocalContext.current

    CompositionLocalProvider(
        LocalContext provides context.createConfigurationContext(
            context.resources.configuration.apply {
                setLocale(Locale("es")) // Establece el idioma a español
            }
        )
    ) {
        val availableDates2 = availableDates.map { date ->
            // Convertir cada fecha al inicio del día en UTC
            date.atStartOfDay(ZoneOffset.UTC).toInstant()
        }

        val selectableSpecificDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val dateInstant = Instant.ofEpochMilli(utcTimeMillis)
                // Comparamos directamente con la lista de instantes
                return availableDates2.contains(dateInstant)
            }
        }

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = null,
            yearRange = 2024..2024,
            selectableDates = selectableSpecificDates,
        )

        val datePickerDialogVisible = remember { mutableStateOf(true) }


        if (datePickerDialogVisible.value) {
            DatePickerDialog(
                onDismissRequest = {
                    datePickerDialogVisible.value = false
                    onDismissRequest()
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                                    datePickerState.selectedDateMillis?.let { millis ->
                                        val selectedDate = Instant.ofEpochMilli(millis)
                                            .atZone(ZoneOffset.UTC)
                                            .toLocalDate()
                                        if (availableDates.contains(selectedDate)) {
                                            onDateSelected(selectedDate)
                                        }
                                    }
//                            onDateSelected(datePickerState.selectedDateMillis)
                            datePickerDialogVisible.value = false
                        }
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            datePickerDialogVisible.value = false
                            onDismissRequest()
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    showModeToggle = false, // Oculta el botón de cambio de modo de selección de fecha QUEDA
                    title = null, // Elimina el título predeterminado "Selected date" QUEDA
                    headline = {
                        Text(
                            fontSize = 12.sp,
                            text = "Selecciona una fecha disponible"
                        )
                    }, // Elimina el encabezado predeterminado "Selected date" QUEDA
                    dateFormatter = remember { DatePickerDefaults.dateFormatter() },
                                    colors = DatePickerDefaults.colors(
                                        todayContentColor = Color.Gray, // Elimina el círculo verde,
                                        todayDateBorderColor = Color.Transparent,
                                        dayContentColor = Color.Blue
//            todayDateTextColor = Color.Black, // Color normal para la fecha actual
                                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFieldWithIcon() {
    var selectedDate by remember { mutableStateOf("MM / AAAA") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // Formatea la fecha seleccionada
            selectedDate = "${month + 1}/$year" // El mes es 0-indexado
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    TextField(
        value = selectedDate,
        onValueChange = {},
        label = { Text("") },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select Date")
            }
        }
    )
}


@Preview
@Composable
fun DatePickerPreview() {
    BooKingTheme {
        AvailableDatePickerDialog(
            onDismissRequest = { /*TODO*/ },
            onDateSelected = {},
            availableDates = getAvailableDates()
        )
    }
}