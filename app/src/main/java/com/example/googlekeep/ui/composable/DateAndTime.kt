package com.example.googlekeep.ui.composable

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    onSelectedDate: (Long?) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis > System.currentTimeMillis() - (1000 * 60 * 60 * 24)
            }
        }
    )
    DatePickerDialog(
        onDismissRequest = { onSelectedDate(null) },
        confirmButton = {
            Button(
                onClick = { onSelectedDate(
                    datePickerState.selectedDateMillis
                ) }
            ) {
                Text("Seleccionar")
            }
        },
        dismissButton = {
            Button(
                onClick = { onSelectedDate(null) }
            ) {
                Text("Cancelar")
            }
        },
        content = {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePicker(
    onSelectedDate: (String?) -> Unit
) {
    val timePickerState = rememberTimePickerState(is24Hour = true)
    DatePickerDialog(
        onDismissRequest = {
            onSelectedDate(null)
        },
        confirmButton = {
            Button(
                onClick = {
                    onSelectedDate("${timePickerState.hour}:${timePickerState.minute}")
                }
            ) {
                Text("Seleccionar")
            }
        },
        dismissButton = {
            Button(
                onClick = { onSelectedDate(null) }
            ) {
                Text("Cancelar")
            }
        },
    ) {
        TimePicker(state = timePickerState)
    }
}

fun Long.convertMillisToDate(): String {
    // Create a calendar instance in the default time zone
    val calendar = Calendar.getInstance().apply {
        timeInMillis = this@convertMillisToDate
        // Adjust for the time zone offset to get the correct local date
        val zoneOffset = get(Calendar.ZONE_OFFSET)
        val dstOffset = get(Calendar.DST_OFFSET)
        add(Calendar.MILLISECOND, -(zoneOffset + dstOffset))
    }
    // Format the calendar time in the specified format
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    return sdf.format(calendar.time)
}