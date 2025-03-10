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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.tsunotintime.presentation.components.LoadingIndicator
import com.example.tsunotintime.presentation.components.ProfileButton
import com.example.tsunotintime.presentation.components.ProfileField
import com.example.tsunotintime.presentation.screen.NavigationScreen
import com.example.tsunotintime.presentation.state.FetchDataState
import com.example.tsunotintime.presentation.state.InputType
import com.example.tsunotintime.presentation.state.LoginEvent
import com.example.tsunotintime.presentation.state.ProfileState
import com.example.tsunotintime.presentation.state.RegisterCredentialsState
import com.example.tsunotintime.presentation.state.RegisterEvent
import com.example.tsunotintime.presentation.viewModel.LoginViewModel
import com.example.tsunotintime.presentation.viewModel.ProfileViewModel
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
import kotlinx.coroutines.launch
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










