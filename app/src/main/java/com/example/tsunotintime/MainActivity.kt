package com.example.tsunotintime

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.domain.entity.RequestModel
import com.example.tsunotintime.presentation.components.FullRequestCard
import com.example.tsunotintime.presentation.screen.NavigationScreen
import com.example.tsunotintime.ui.theme.SecondaryColor
import com.example.tsunotintime.ui.theme.TSUNotInTimeTheme

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
                    NavigationScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(color = SecondaryColor), navController = navController
                    )
                }
            }
        }
    }
}



