package com.example.tsunotintime

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tsunotintime.presentation.components.AuthButton
import com.example.tsunotintime.presentation.components.CustomInputField
import com.example.tsunotintime.presentation.screen.LoginScreen
import com.example.tsunotintime.presentation.state.InputType
import com.example.tsunotintime.presentation.state.LoginEvent
import com.example.tsunotintime.presentation.viewModel.LoginViewModel
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryButton
import com.example.tsunotintime.ui.theme.SecondaryColor
import com.example.tsunotintime.ui.theme.TSUNotInTimeTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSUNotInTimeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    RegisterScreen(LoginViewModel())
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(loginViewModel: LoginViewModel) {
    val loginFormState = loginViewModel.state.value
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
                .padding(start = 24.dp, top = 36.dp)
                .align(Alignment.TopStart)
        ) {
            IconButton(
                onClick = {},
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
                .padding(top = 100.dp)
                .verticalScroll(scrollState)
                .imePadding()
                .navigationBarsPadding(),
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
            val fieldList = listOf(
                stringResource(R.string.last_name_input_field),
                stringResource(R.string.name_input_field),
                stringResource(R.string.middle_name_input_field),
                stringResource(R.string.email),
                stringResource(R.string.password),
                stringResource(R.string.password_confirmation)
            )

            fieldList.forEachIndexed() { index, field ->
                CustomInputField(
                    value = "",
                    placeholder = field,
                    onFocusChange = {},
                    onValueChange = {},
                    hasError = false,
                    errorMessage = null,
                    isPasswordField = (
                            field == stringResource(R.string.password)
                                    || field == stringResource(R.string.password_confirmation)),
                    labelIcon = null
                )
                if (index != fieldList.size - 1) {
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
            Spacer(Modifier.height(30.dp))
            AuthButton(
                buttonText = stringResource(R.string.Register_label),
                modifier = Modifier.fillMaxWidth(0.8f),
                isEnabled = loginFormState.isValid,
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





