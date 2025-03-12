package com.example.tsunotintime.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.tsunotintime.R

@Composable
fun ErrorComponent(message: String, onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = stringResource(R.string.wrong_message),
                textAlign = TextAlign.Center
            )
        },
        text = { Text(text = message, textAlign = TextAlign.Center) },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = onRetry) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        },
        modifier = Modifier
    )
}