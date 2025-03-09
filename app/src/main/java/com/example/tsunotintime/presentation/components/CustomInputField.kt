package com.example.tsunotintime.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsunotintime.R
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.SecondaryButton

@Composable
fun CustomInputField(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    hasError: Boolean = false,
    errorMessage: String? = null,
    onFocusChange: (FocusState) -> Unit,
    onValueChange: (String) -> Unit,
    textColor: Color = Color.Black,
    isPasswordField: Boolean = false,
    labelIcon: Int? = null,
) {
    var isFocused by remember { mutableStateOf(false) }
    var passwordVisibility by remember { mutableStateOf(!isPasswordField) }

    val errorColor = Color.Red
    val focusColor = SecondaryButton
    val standartColor = Color.LightGray

    val iconColor = when {
        hasError -> errorColor
        isFocused -> focusColor
        else -> standartColor
    }

    val touched = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
            },
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    labelIcon?.let { painterResource(it) }?.let {
                        Icon(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier.wrapContentSize(),
                            tint = iconColor
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(placeholder)
                }
            },
            modifier = modifier
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    if (touched.value) onFocusChange(focusState);

                }
                .fillMaxWidth(0.8f),
            isError = hasError,
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    )
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = errorColor,
                errorLabelColor = errorColor,
                errorLeadingIconColor = errorColor,
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedBorderColor = focusColor,
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = focusColor,
                unfocusedLabelColor = Color.LightGray
            ),
            visualTransformation = if (isPasswordField && !passwordVisibility) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            shape = RoundedCornerShape(25),
            trailingIcon = {
                if (isPasswordField) {
                    IconButton(
                        onClick = { passwordVisibility = !passwordVisibility }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisibility) {
                                    R.drawable.password_invisibility_icon
                                } else {
                                    R.drawable.password_visibility_icon
                                }
                            ),
                            contentDescription = null,
                            tint = Color.LightGray
                        )
                    }
                }
            }
        )

        if (hasError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = errorColor,
                modifier = Modifier.padding(top = 5.dp, start = 40.dp)
            )
        }
    }
}