package com.example.tsunotintime.presentation.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.tsunotintime.R
import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import com.example.tsunotintime.common.URL.IMAGE_URL
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.presentation.state.RequestDetailsState
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryButton
import com.example.tsunotintime.utils.DateTimeParser.formatIsoDateToDisplay
import com.example.tsunotintime.utils.DateValidator


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FullRequestCard(
    requestState: RequestDetailsState,
    onDismiss: () -> Unit,
    onSave: (
        requestId: String,
        status: RequestStatus,
        images: List<String>,
        description: String,
        absenceDateFrom: String,
        absenceDateTo: String,
        newImages: List<Uri>
    ) -> Unit,
) {
    val context = LocalContext.current
    var localRequestState by remember { mutableStateOf(requestState) }
    var newImages by remember { mutableStateOf(listOf<Uri>()) }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                localRequestState.requestModel?.status?.let { StatusBadge(it) }
                if (localRequestState.requestModel?.status == RequestStatus.Checking) {
                    IconButton(onClick = {
                        localRequestState =
                            localRequestState.copy(isEditing = !localRequestState.isEditing)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.edit_icon),
                            tint = SecondaryButton,
                            contentDescription = null
                        )
                    }
                }
            }

            InfoColumn(
                stringResource(R.string.initials),
                "${localRequestState.requestModel?.lastName} " +
                        "${localRequestState.requestModel?.firstName} " +
                        "${localRequestState.requestModel?.middleName}"
            )
            HorizontalDivider(color = Color.LightGray, modifier = Modifier.height(2.dp))

            Text(
                stringResource(R.string.date_period),
                fontSize = 12.sp,
                color = Color.Gray,
                fontFamily = Nunito,
                fontWeight = FontWeight.Medium
            )

            if (localRequestState.isEditing) {
                DateTimePickerField(
                    stringResource(R.string.from_date),
                    localRequestState.requestModel?.absenceDateFrom ?: EMPTY_RESULT,
                    { newDate ->
                        localRequestState = localRequestState.copy(
                            requestModel = localRequestState.requestModel?.copy(absenceDateFrom = newDate)
                        )
                    },
                    null
                )
                Spacer(modifier = Modifier.height(8.dp))
                DateTimePickerField(
                    stringResource(R.string.date_to),
                    localRequestState.requestModel?.absenceDateTo ?: EMPTY_RESULT,
                    { newDate ->
                        localRequestState = localRequestState.copy(
                            requestModel = localRequestState.requestModel?.copy(absenceDateTo = newDate)
                        )
                    },
                    localRequestState.requestModel?.absenceDateFrom
                )
                Spacer(Modifier.height(15.dp))
                HorizontalDivider(color = Color.LightGray, modifier = Modifier.height(2.dp))
            } else {
                Text(
                    "${formatIsoDateToDisplay(localRequestState.requestModel?.absenceDateFrom ?: EMPTY_RESULT)} - ${
                        formatIsoDateToDisplay(localRequestState.requestModel?.absenceDateTo ?: EMPTY_RESULT)
                    }",
                    fontFamily = Nunito,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )
                HorizontalDivider(color = Color.LightGray, modifier = Modifier.height(2.dp))
            }

            InfoColumn(
                stringResource(R.string.create_time),
                formatIsoDateToDisplay(
                    localRequestState.requestModel?.createTime ?: EMPTY_RESULT
                )
            )
            HorizontalDivider(color = Color.LightGray, modifier = Modifier.height(2.dp))
            if (localRequestState.requestModel?.checkerUsername?.isNotEmpty() == true) {
                InfoColumn(
                    stringResource(R.string.checked_by),
                    localRequestState.requestModel?.checkerUsername ?: EMPTY_RESULT
                )
                HorizontalDivider(color = Color.LightGray, modifier = Modifier.height(2.dp))
            }

            if (localRequestState.isEditing) {
                OutlinedTextField(
                    value = localRequestState.requestModel?.description ?: EMPTY_RESULT,
                    onValueChange = {
                        localRequestState = localRequestState.copy(
                            requestModel = localRequestState.requestModel?.copy(description = it)
                        )
                    },
                    label = { Text(stringResource(R.string.description)) },
                    placeholder = { Text(EMPTY_RESULT) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SecondaryButton,
                        unfocusedLabelColor = SecondaryButton,
                        unfocusedBorderColor = SecondaryButton,
                        focusedLabelColor = SecondaryButton
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = Color.LightGray, modifier = Modifier.height(2.dp))
            } else {
                InfoColumn(
                    "Описание",
                    localRequestState.requestModel?.description ?: EMPTY_RESULT
                )
                HorizontalDivider(color = Color.LightGray, modifier = Modifier.height(2.dp))
            }

            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.images), fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Medium
                )
                if (localRequestState.isEditing) {
                    Spacer(modifier = Modifier.height(8.dp))
                    PickImage({ uri ->
                        uri?.let {
                            newImages = newImages + uri
                        }
                    }, context)
                }
            }
            Spacer(Modifier.height(8.dp))
            LazyRow {
                localRequestState.requestModel?.images?.let { images ->
                    items(images.size) { index ->
                        val imageUrl = IMAGE_URL + images[index]
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
                                        openImageInGallery(context, imageUrl)
                                    },
                                loading = placeholder({ LoadingIndicator() })
                            )
                            if (localRequestState.isEditing) {
                                IconButton(
                                    onClick = {
                                        val updatedImages = images.toMutableList()
                                        updatedImages.removeAt(index)

                                        localRequestState = localRequestState.copy(
                                            requestModel = localRequestState.requestModel?.copy(
                                                images = updatedImages
                                            )
                                        )
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
                newImages.let { images ->
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
                            if (localRequestState.isEditing) {
                                IconButton(
                                    onClick = {
                                        newImages = newImages - images[index]
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

            Spacer(modifier = Modifier.height(16.dp))

            if (localRequestState.isEditing) {
                Button(
                    onClick = {
                        val validationResult = DateValidator.validateDateTime(
                            localRequestState.requestModel?.absenceDateFrom ?: EMPTY_RESULT,
                            localRequestState.requestModel?.absenceDateTo ?: EMPTY_RESULT,
                            context
                        )
                        if (validationResult.result) {
                            onSave(
                                localRequestState.requestModel?.id ?: EMPTY_RESULT,
                                localRequestState.requestModel?.status ?: RequestStatus.Checking,
                                localRequestState.requestModel?.images ?: emptyList(),
                                localRequestState.requestModel?.description ?: EMPTY_RESULT,
                                localRequestState.requestModel?.absenceDateFrom ?: EMPTY_RESULT,
                                localRequestState.requestModel?.absenceDateTo ?: EMPTY_RESULT,
                                newImages
                            )
                        } else {
                            localRequestState =
                                localRequestState.copy(
                                    errorMessage = validationResult.errorMessage ?: EMPTY_RESULT
                                )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryButton)
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (localRequestState.errorMessage.isNotEmpty()) {
                    Text(
                        text = localRequestState.errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

fun openImageInGallery(context: Context, imageUri: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(Uri.parse(imageUri), context.getString(R.string.image_input))
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    context.startActivity(intent)
}
