package com.example.tsunotintime.presentation.screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tsunotintime.R
import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import com.example.tsunotintime.presentation.components.AuthButton
import com.example.tsunotintime.presentation.components.DateTimePickerField
import com.example.tsunotintime.presentation.components.ErrorComponent
import com.example.tsunotintime.presentation.components.LoadingIndicator
import com.example.tsunotintime.presentation.components.PickImage
import com.example.tsunotintime.presentation.components.openImageInGallery
import com.example.tsunotintime.presentation.state.FetchDataState
import com.example.tsunotintime.presentation.state.RequestFormState
import com.example.tsunotintime.presentation.viewModel.AddRequestViewModel
import com.example.tsunotintime.presentation.viewModel.AuthViewModel
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryButton
import com.example.tsunotintime.ui.theme.SecondaryColor

@Composable
fun AddRequestScreen(
    back: () -> Unit,
    viewModel: AddRequestViewModel,
    authViewModel: AuthViewModel,
    toLogin: () -> Unit
) {
    val tokenState by authViewModel.tokenState.collectAsState()
    val requestFormState by viewModel.requestFormState.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    when (screenState.currentState) {
        is FetchDataState.Loading -> LoadingIndicator()
        is FetchDataState.Success -> {
            back()
        }

        is FetchDataState.Initial -> {
            RequestForm(
                requestFormState,
                back,
                { viewModel.addRequest() },
                onDateFromChange = { date -> viewModel.setAbsenceDateFrom(date) },
                onDateFromTo = { date -> viewModel.setAbsenceDateTo(date) },
                onDescriptionChange = { description -> viewModel.setDescription(description) },
                onImageChange = { images -> viewModel.setImages(images) })
        }

        is FetchDataState.Error -> ErrorComponent(
            message = (screenState.currentState as FetchDataState.Error).message,
            onRetry = {
                if (tokenState) viewModel.addRequest() else {
                    toLogin()
                }
            },
            onDismiss = {
                if (tokenState) viewModel.toInitialState() else {
                    toLogin()
                }
            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RequestForm(
    requestFormState: RequestFormState,
    back: () -> Unit,
    addAction: () -> Unit,
    onDateFromChange: (String) -> Unit,
    onDateFromTo: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onImageChange: (List<Uri>) -> Unit
) {
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SecondaryColor)
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
                text = stringResource(R.string.new_request),
                color = PrimaryColor,
                fontSize = 20.sp,
                lineHeight = 48.sp,
                fontFamily = Nunito,
                fontWeight = FontWeight.Black
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 100.dp)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.fill_form),
                color = PrimaryColor,
                fontSize = 20.sp,
                lineHeight = 22.sp,
                fontFamily = Nunito,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(20.dp))
            DateTimePickerField(
                stringResource(R.string.from_date),
                requestFormState.absenceDateFrom,
                { newDate ->
                    onDateFromChange(newDate)
                },
                null
            )
            Spacer(modifier = Modifier.height(8.dp))
            DateTimePickerField(
                stringResource(R.string.date_to),
                requestFormState.absenceDateTo,
                { newDate ->
                    onDateFromTo(newDate)
                },
                //requestFormState.absenceDateFrom
                null
            )
            OutlinedTextField(
                value = requestFormState.description,
                onValueChange = {
                    onDescriptionChange(it)
                },
                label = { Text(stringResource(R.string.description)) },
                placeholder = { Text(EMPTY_RESULT) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SecondaryButton,
                    unfocusedLabelColor = SecondaryButton,
                    unfocusedBorderColor = SecondaryButton,
                    focusedLabelColor = SecondaryButton,
                    cursorColor = SecondaryButton
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.images), fontSize = 16.sp,
                    color = Color.Gray,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                PickImage({ uri ->
                    uri?.let {
                        onImageChange(requestFormState.images + uri)
                    }
                }, context)

            }

            LazyRow {
                requestFormState.images.let { images ->
                    items(images.size) { index ->
                        val imageUrl = images[index]
                        Box(modifier = Modifier.padding(end = 8.dp)) {
                            GlideImage(
                                model = imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                    .clickable {
                                        openImageInGallery(context, imageUrl.toString())
                                    }
                            )
                            IconButton(
                                onClick = {
                                    onImageChange(requestFormState.images - imageUrl)
                                },
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.TopEnd)
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }

                        }
                    }
                }
            }
        }
        AuthButton(
            stringResource(R.string.add_new_request),
            Modifier
                .padding(24.dp)
                .fillMaxWidth(0.95f)
                .align(Alignment.BottomCenter),
            requestFormState.isValid
        ) { addAction() }
    }
}