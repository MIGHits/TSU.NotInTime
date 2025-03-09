package com.example.tsunotintime.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsunotintime.R
import com.example.tsunotintime.presentation.components.AuthButton
import com.example.tsunotintime.presentation.components.CustomInputField
import com.example.tsunotintime.presentation.state.InputType
import com.example.tsunotintime.presentation.state.RegisterEvent
import com.example.tsunotintime.presentation.viewModel.RegisterViewModel
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryButton
import com.example.tsunotintime.ui.theme.SecondaryColor

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel,
    back: () -> Unit,
    toLogin: () -> Unit
) {
    val registerFormState = registerViewModel.state.value
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SecondaryColor),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 12.dp)
                .align(Alignment.TopStart)
        ) {
            IconButton(
                onClick = { back() },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = null,
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.DarkGray
                )
            }

            Text(
                text = stringResource(R.string.register_top_header),
                color = PrimaryColor,
                fontSize = 20.sp,
                lineHeight = 48.sp,
                fontFamily = Nunito,
                fontWeight = FontWeight.Black
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.tsu_icon),
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = SecondaryButton
            )
            Text(
                text = stringResource(R.string.make_account),
                color = Color.Black,
                fontSize = 18.sp,
                lineHeight = 40.sp,
                fontFamily = Nunito,
                fontWeight = FontWeight.Black
            )
            Spacer(Modifier.height(30.dp))
            val fields = listOf(
                Triple(
                    stringResource(R.string.last_name_input_field),
                    InputType.LAST_NAME,
                    registerViewModel.state.value.lastName
                ),
                Triple(
                    stringResource(R.string.name_input_field),
                    InputType.NAME,
                    registerViewModel.state.value.name
                ),
                Triple(
                    stringResource(R.string.middle_name_input_field),
                    InputType.MIDDLE_NAME,
                    registerViewModel.state.value.middleName
                ),
                Triple(
                    stringResource(R.string.email),
                    InputType.EMAIL,
                    registerViewModel.state.value.email
                ),
                Triple(
                    stringResource(R.string.password),
                    InputType.PASSWORD,
                    registerViewModel.state.value.password
                ),
                Triple(
                    stringResource(R.string.password_confirmation),
                    InputType.PASSWORD_CONFIRMATION,
                    registerViewModel.state.value.passwordConfirmation
                )
            )

            fields.forEachIndexed { index, (placeholder, inputType, fieldState) ->
                CustomInputField(
                    value = fieldState.text,
                    placeholder = placeholder,
                    onFocusChange = {
                        registerViewModel.createEvent(
                            RegisterEvent.FormChange(inputType)
                        )
                    },
                    onValueChange = { newValue ->
                        val event = when (inputType) {
                            InputType.LAST_NAME -> RegisterEvent.EnteredLastName(newValue)
                            InputType.NAME -> RegisterEvent.EnteredName(newValue)
                            InputType.MIDDLE_NAME -> RegisterEvent.EnteredMiddleName(newValue)
                            InputType.EMAIL -> RegisterEvent.EnteredEmail(newValue)
                            InputType.PASSWORD -> RegisterEvent.EnteredPassword(newValue)
                            InputType.PASSWORD_CONFIRMATION -> RegisterEvent.EnteredPasswordConfirmation(
                                newValue
                            )

                        }

                        event.let {
                            registerViewModel.createEvent(it)
                            registerViewModel.createEvent(
                                RegisterEvent.FormChange(inputType)
                            )
                        }
                    },
                    hasError = !fieldState.isValid && !fieldState.isInitialState,
                    errorMessage = fieldState.errorMessage,
                    isPasswordField = inputType == InputType.PASSWORD || inputType == InputType.PASSWORD_CONFIRMATION,
                    labelIcon = null
                )
                if (index != fields.size - 1) {
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }

            Spacer(Modifier.height(30.dp))
            AuthButton(
                buttonText = stringResource(R.string.Register_label),
                modifier = Modifier.fillMaxWidth(0.8f),
                isEnabled = registerFormState.isValid,
                onClick = {}
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.has_account),
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    lineHeight = 36.sp,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(5.dp))
                ClickableText(
                    text = AnnotatedString(stringResource(R.string.Enter)),
                    onClick = { toLogin() },
                    style = TextStyle(
                        color = SecondaryButton,
                        fontSize = 14.sp,
                        lineHeight = 36.sp,
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }

        }
    }
}