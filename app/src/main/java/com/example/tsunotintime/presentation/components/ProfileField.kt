package com.example.tsunotintime.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.PrimaryColor

@Composable
fun ProfileField(fieldName: String, value: String) {
    Column(modifier = Modifier.padding(top = 15.dp)) {
        Text(
            text = fieldName,
            modifier = Modifier,
            fontFamily = Nunito,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Text(
            text = value,
            modifier = Modifier,
            fontFamily = Nunito,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = PrimaryColor
        )
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
    }
}