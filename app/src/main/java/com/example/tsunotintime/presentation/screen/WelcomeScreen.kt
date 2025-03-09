package com.example.tsunotintime.presentation.screen

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsunotintime.R
import com.example.tsunotintime.presentation.components.AuthButton
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun WelcomeScreen(toLogin: () -> Unit, toRegistration: () -> Unit) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(systemUiController) {
        systemUiController.setStatusBarColor(
            color = PrimaryColor,
            darkIcons = false
        )
        systemUiController.setSystemBarsColor(
            color = PrimaryColor

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.tsu_icon),
                    null, tint = PrimaryColor,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.size(10.dp, 15.dp))
                Text(
                    text = stringResource(R.string.app_tittle),
                    color = PrimaryColor,
                    fontSize = 20.sp,
                    lineHeight = 48.sp,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Black
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            AuthButton(
                stringResource(R.string.register),
                isEnabled = true,
                onClick = { toRegistration() }, modifier = Modifier.fillMaxWidth(0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            AuthButton(
                stringResource(R.string.Enter),
                isEnabled = true,
                onClick = { toLogin() },
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }
    }
}