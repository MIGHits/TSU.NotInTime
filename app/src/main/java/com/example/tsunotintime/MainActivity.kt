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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.tsunotintime.presentation.components.AuthButton
import com.example.tsunotintime.presentation.components.CustomInputField
import com.example.tsunotintime.presentation.screen.NavigationScreen
import com.example.tsunotintime.presentation.state.InputType
import com.example.tsunotintime.presentation.state.RegisterCredentialsState
import com.example.tsunotintime.presentation.state.RegisterEvent
import com.example.tsunotintime.presentation.viewModel.LoginViewModel
import com.example.tsunotintime.presentation.viewModel.RegisterViewModel
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.PrimaryButton
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryButton
import com.example.tsunotintime.ui.theme.SecondaryColor
import com.example.tsunotintime.ui.theme.TSUNotInTimeTheme
import com.example.tsunotintime.ui.theme.editButtonBackground
import com.example.tsunotintime.ui.theme.exitButtonBackground
import com.example.tsunotintime.ui.theme.exitButtonIconTint
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSUNotInTimeTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .consumeWindowInsets(PaddingValues())
                        .imePadding()
                ) { innerPadding ->
                    ProfileScreen()
                    /* NavigationScreen(
                         modifier = Modifier
                             .padding(innerPadding)
                             .background(color = SecondaryColor), navController = navController
                     )*/
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryColor)
    ) {
        ProfileButton(
            R.drawable.exit_icon,
            exitButtonIconTint,
            exitButtonBackground,
            {}, Modifier
                .align(Alignment.TopEnd)
                .padding(top = 32.dp, end = 24.dp)
        )
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.profile_icon),
                null,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .size(90.dp)
            )
            Text(
                modifier = Modifier.padding(top = 5.dp),
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = PrimaryColor,
                            fontFamily = Nunito,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("Добрый вечер,\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = PrimaryColor,
                            fontFamily = Nunito,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    ) {
                        append("Пользователь")
                    }
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                val fieldList = listOf(
                    Pair("Имя", "Игорь"),
                    Pair("Фамилия", "Мяков"),
                    Pair("Отчество", "Николаевич"),
                    Pair("Email", "sepryn4ik@mail.ru"),
                    Pair("Статус", "Студент")
                )

                fieldList.forEach { pair ->
                    ProfileField(pair.first, pair.second)
                }
            }
            ProfileButton(
                R.drawable.edit_password_icon,
                PrimaryButton,
                editButtonBackground,
                {}, Modifier
            )
        }
    }
}

@Composable
fun ProfileButton(
    icon: Int,
    iconTint: Color,
    backgroundTint: Color,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(backgroundTint)
            .padding(8.dp)
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = iconTint
            )
        }
    }
}

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



