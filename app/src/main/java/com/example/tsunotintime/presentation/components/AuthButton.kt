package com.example.tsunotintime.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.SecondaryButton
import com.example.tsunotintime.ui.theme.SecondaryColor

@Composable
fun AuthButton(
    buttonText: String,
    modifier: Modifier? = Modifier,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier ?: Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
        onClick = onClick,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) {
                SecondaryButton
            } else Color.LightGray,
            contentColor = SecondaryColor
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp
        ),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
            top = 12.dp,
            bottom = 12.dp
        ),
    ) {
        Text(
            text = buttonText,
            color = if (isEnabled) Color.White else Color.DarkGray,
            fontSize = 18.sp,
            lineHeight = 20.sp,
            fontFamily = Nunito,
            fontWeight = FontWeight.Bold
        )
    }
}