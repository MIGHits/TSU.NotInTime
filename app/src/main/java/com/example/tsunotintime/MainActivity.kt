package com.example.tsunotintime

import android.app.Activity
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.SecondaryColor
import com.example.tsunotintime.ui.theme.TSUNotInTimeTheme
import com.example.tsunotintime.ui.theme.PrimaryButton
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryButton
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = PrimaryColor.toArgb()
        setContent {
            TSUNotInTimeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    GreetingScreen()
                }
            }
        }
    }
}

@Composable
fun GreetingScreen() {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(systemUiController) {
        systemUiController.setStatusBarColor(
            color = PrimaryColor,
            darkIcons = false
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SecondaryColor)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 200.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.welcome_stub),
                null,
                modifier = Modifier
                    .wrapContentSize()
            )
            Text(
                text = "Не пропускай — будь в теме!",
                color = PrimaryColor,
                fontSize = 18.sp,
                lineHeight = 36.sp,
                fontFamily = Nunito,
                fontWeight = FontWeight.Black
            )
        }

        Row(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(PrimaryColor, SecondaryColor),
                        start = Offset(0f, 5f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.tsu_icon),
                null, tint = Color.White,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.size(15.dp, 15.dp))
            Text(
                text = stringResource(R.string.app_tittle),
                color = Color.White,
                fontSize = 20.sp,
                lineHeight = 48.sp,
                fontFamily = Nunito,
                fontWeight = FontWeight.Black
            )
        }

        Column(
            modifier = Modifier
                .systemBarsPadding()
                .padding(bottom = 16.dp)
                .fillMaxWidth(0.85f)
                .wrapContentHeight()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthButton(stringResource(R.string.register))
            AuthButton(stringResource(R.string.Enter))
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TSUNotInTimeTheme {
        Greeting("Android")
    }
}*/
@Composable
fun AuthButton(buttonText: String) {
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = SecondaryButton,
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
        )
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            fontSize = 18.sp,
            lineHeight = 20.sp,
            fontFamily = Nunito,
            fontWeight = FontWeight.Bold
        )
    }
}