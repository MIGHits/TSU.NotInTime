package com.example.tsunotintime.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsunotintime.R
import com.example.tsunotintime.presentation.components.AuthButton
import com.example.tsunotintime.presentation.components.CustomInputField
import com.example.tsunotintime.presentation.components.ErrorComponent
import com.example.tsunotintime.presentation.components.LoadingIndicator
import com.example.tsunotintime.presentation.components.ProfileButton
import com.example.tsunotintime.presentation.components.ProfileField
import com.example.tsunotintime.presentation.state.FetchDataState
import com.example.tsunotintime.presentation.state.ProfileEvent
import com.example.tsunotintime.presentation.state.ProfileState
import com.example.tsunotintime.presentation.viewModel.ProfileViewModel
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.PrimaryButton
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryButton
import com.example.tsunotintime.ui.theme.SecondaryColor
import com.example.tsunotintime.ui.theme.exitButtonBackground
import com.example.tsunotintime.ui.theme.exitButtonIconTint
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, back: () -> Unit, logoutAction: () -> Unit) {
    val screenState = viewModel.screenState.value.currentState
    val profileState = viewModel.profileState.value

    when (screenState) {
        is FetchDataState.Success -> ProfileForm(
            profileState,
            back,
            { value -> viewModel.createEvent(ProfileEvent.EnteredPassword(value)) },
            { viewModel.createEvent(ProfileEvent.ButtonClick) },
            {
                viewModel.createEvent(ProfileEvent.Logout)
                logoutAction()
            })

        is FetchDataState.Loading -> LoadingIndicator()
        is FetchDataState.Error -> ErrorComponent(
            message = screenState.message,
            { viewModel.getProfile() }, {})

        FetchDataState.Initial -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileForm(
    profileState: ProfileState,
    back: () -> Unit,
    onChange: (String) -> Unit,
    buttonAction: () -> Unit,
    logoutAction: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { sheetValue ->
            when (sheetValue) {
                SheetValue.Hidden -> true
                SheetValue.PartiallyExpanded -> true
                SheetValue.Expanded -> true
            }
        }
    )

    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SecondaryColor)
        ) {
            ProfileButton(
                R.drawable.exit_icon,
                exitButtonIconTint,
                exitButtonBackground,
                { logoutAction() },
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 34.dp, end = 24.dp)
                    .size(48.dp)
                    .padding(8.dp)
            )

            IconButton(
                onClick = { back() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 32.dp, start = 24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = null,
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.DarkGray
                )
            }

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
                        Pair(stringResource(R.string.name), profileState.name),
                        Pair(stringResource(R.string.last_name), profileState.lastName),
                        Pair(stringResource(R.string.middle_name), profileState.middleName),
                        Pair(stringResource(R.string.email_field), profileState.email),
                        Pair(stringResource(R.string.status), profileState.status)
                    )

                    fieldList.forEach { pair ->
                        if (pair.second.isNotBlank()) {
                            ProfileField(pair.first, pair.second)
                        }
                    }
                }
                ProfileButton(
                    R.drawable.edit_password_icon,
                    PrimaryButton,
                    SecondaryColor,
                    { showBottomSheet = true }, Modifier
                )
                Text(
                    text = stringResource(R.string.change_password),
                    color = SecondaryButton,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    lineHeight = 16.sp,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.bottom_sheet_dialog_header),
                    color = PrimaryColor,
                    fontSize = 18.sp,
                    lineHeight = 36.sp,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Bold
                )
                CustomInputField(
                    value = profileState.newPassword.text,
                    placeholder = stringResource(R.string.new_password),
                    onFocusChange = {
                    },
                    onValueChange = { text ->
                        onChange(text)
                    },
                    hasError = false,
                    errorMessage = null,
                    isPasswordField = true,
                    labelIcon = R.drawable.password_icon
                )
                Spacer(Modifier.height(30.dp))
                AuthButton(
                    stringResource(R.string.save),
                    isEnabled = profileState.newPassword.isValid,
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                        buttonAction()
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }
        }
    }
}