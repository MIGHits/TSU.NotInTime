package com.example.tsunotintime.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.example.tsunotintime.presentation.state.LoginEvent
import com.example.tsunotintime.presentation.viewModel.LoginViewModel
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryButton
import com.example.tsunotintime.ui.theme.SecondaryColor


@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    val loginFormState = loginViewModel.state.value
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SecondaryColor)
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .padding(start = 24.dp, top = 36.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                painter = painterResource(R.drawable.back_icon),
                contentDescription = null,
                modifier = Modifier.wrapContentSize(),
                tint = Color.DarkGray
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .imePadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.login_art),
                contentDescription = null,
                modifier = Modifier.wrapContentSize()
            )
            Text(
                text = stringResource(R.string.Login_header),
                color = PrimaryColor,
                fontSize = 20.sp,
                lineHeight = 48.sp,
                fontFamily = Nunito,
                fontWeight = FontWeight.Black
            )

            CustomInputField(
                value = loginFormState.email.text,
                placeholder = stringResource(R.string.email),
                onFocusChange = {
                    loginViewModel.createEvent(
                        LoginEvent.FormChange(InputType.EMAIL)
                    )
                },
                onValueChange = { text ->
                    loginViewModel.createEvent(LoginEvent.EnteredEmail(text))
                    loginViewModel.createEvent(LoginEvent.FormChange(InputType.EMAIL))
                },
                hasError = !loginFormState.email.isValid,
                errorMessage = loginFormState.email.errorMessage,
                isPasswordField = false,
                labelIcon = R.drawable.mail_icon
            )
            Spacer(modifier = Modifier.size(10.dp))
            CustomInputField(
                value = loginFormState.password.text,
                placeholder = stringResource(R.string.password),
                onFocusChange = {
                    loginViewModel.createEvent(
                        LoginEvent.FormChange(InputType.PASSWORD)
                    )
                },
                onValueChange = { text ->
                    loginViewModel.createEvent(LoginEvent.EnteredPassword(text))
                    loginViewModel.createEvent(LoginEvent.FormChange(InputType.PASSWORD))
                },
                hasError = !loginFormState.password.isValid,
                errorMessage = loginFormState.password.errorMessage,
                isPasswordField = true,
                labelIcon = R.drawable.password_icon
            )
            Spacer(modifier = Modifier.height(30.dp))
            AuthButton(
                buttonText = stringResource(R.string.Enter),
                modifier = Modifier.fillMaxWidth(0.8f),
                isEnabled = loginFormState.isValid,
                onClick = {}
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.without_account),
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    lineHeight = 36.sp,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(5.dp))
                ClickableText(
                    text = AnnotatedString(stringResource(R.string.Register_label)),
                    onClick = {},
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