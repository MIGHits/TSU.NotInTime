package com.example.tsunotintime.presentation.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.ContextThemeWrapper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tsunotintime.R
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryButton
import com.example.tsunotintime.utils.DateTimeParser.formatIsoDateToDisplay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun DateTimePickerField(
    label: String,
    initialDateTime: String,
    onDateTimeSelected: (String) -> Unit,
    minDateTime: String? = null
) {
    val context = LocalContext.current
    val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    var displayDateTime by remember { mutableStateOf(formatIsoDateToDisplay(initialDateTime)) }

    val calendar = Calendar.getInstance()
    minDateTime?.let {
        Calendar.getInstance().apply { time = isoFormatter.parse(it) ?: Date() }
    }

    val datePickerDialog = remember {
        val contextThemeWrapper = ContextThemeWrapper(context, R.style.CustomTimePickerDialogTheme)
        DatePickerDialog(
            contextThemeWrapper,
            { _, year, month, day ->
                Calendar.getInstance().apply { set(year, month, day) }
                val timePickerDialog = TimePickerDialog(
                    contextThemeWrapper,
                    { _, hour, minute ->
                        val selectedDateTime =
                            Calendar.getInstance().apply { set(year, month, day, hour, minute) }
                        val isoDateTime = isoFormatter.format(selectedDateTime.time)
                        onDateTimeSelected(isoDateTime)
                        displayDateTime =
                            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(
                                selectedDateTime.time
                            )
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePickerDialog.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            val minDate = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            datePicker.minDate = minDate.timeInMillis
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() }
    ) {
        OutlinedTextField(
            value = displayDateTime,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = PrimaryColor,
                disabledLabelColor = SecondaryButton,
                disabledBorderColor = SecondaryButton
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}