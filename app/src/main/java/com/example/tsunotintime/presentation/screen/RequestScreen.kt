package com.example.tsunotintime.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tsunotintime.R
import com.example.tsunotintime.data.models.RequestShortModel
import com.example.tsunotintime.presentation.components.ErrorComponent
import com.example.tsunotintime.presentation.components.FullRequestCard
import com.example.tsunotintime.presentation.components.LoadingIndicator
import com.example.tsunotintime.presentation.components.RequestCard
import com.example.tsunotintime.presentation.state.BadgeState
import com.example.tsunotintime.presentation.state.FetchDataState
import com.example.tsunotintime.presentation.state.RequestDetailsState
import com.example.tsunotintime.presentation.viewModel.AuthViewModel
import com.example.tsunotintime.presentation.viewModel.RequestViewModel
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryButton
import com.example.tsunotintime.ui.theme.SecondaryColor
import com.example.tsunotintime.ui.theme.approvedBadgeBackground
import com.example.tsunotintime.ui.theme.approvedBadgeTextTint
import com.example.tsunotintime.ui.theme.exitButtonBackground
import com.example.tsunotintime.ui.theme.exitButtonIconTint
import com.example.tsunotintime.ui.theme.pendingBadgeBackground
import com.example.tsunotintime.ui.theme.pendingBadgeTextTint
import com.example.tsunotintime.utils.DateTimeParser.formatIsoDateToDisplay
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun RequestScreen(
    viewModel: RequestViewModel, toProfile: () -> Unit, toAddScreen: () -> Unit,
    authViewModel: AuthViewModel,
    toLogin: () -> Unit
) {
    val tokenState = authViewModel.tokenState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    val requestState by viewModel.requestState.collectAsState()

    when (screenState.currentState) {
        is FetchDataState.Success -> {
            val requests = requestState.requests ?: emptyList()
            viewModel.updateBadges(requests)
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { viewModel.getRequests() }
            ) {
                RequestListScreen(
                    requestList = requests,
                    onSelect = { requestId -> viewModel.getDetails(requestId) },
                    requestState = requestState.requestDetails,
                    badgeState = requestState.badgeState,
                    toProfile = toProfile,
                    viewModel = viewModel,
                    toAddScreen = toAddScreen
                )
            }
        }

        is FetchDataState.Loading -> LoadingIndicator()
        is FetchDataState.Error -> ErrorComponent(
            message = (screenState.currentState as FetchDataState.Error).message,
            onRetry = {
                if (tokenState.value) viewModel.getRequests() else {
                    toLogin()
                }
            },
            onDismiss = {
                if (tokenState.value){} else {
                    toLogin()
                }
            }
        )

        FetchDataState.Initial -> {
            LoadingIndicator()
        }
    }
}

@Composable
fun RequestListScreen(
    requestList: List<RequestShortModel>,
    onSelect: (String) -> Unit,
    requestState: RequestDetailsState,
    viewModel: RequestViewModel,
    badgeState: BadgeState,
    toProfile: () -> Unit,
    toAddScreen: () -> Unit
) {
    var selectItem by remember { mutableStateOf<RequestShortModel?>(null) }
    selectItem?.let {
        onSelect(it.id)
        Dialog(onDismissRequest = {
            selectItem = null
        }) {
            val cardState by viewModel.cardDialogState.collectAsState()
            requestState.requestModel?.let {
                when (cardState.currentState) {
                    is FetchDataState.Loading -> LoadingIndicator()
                    is FetchDataState.Success -> {
                        FullRequestCard(
                            requestState = viewModel.requestState.value.requestDetails,
                            onDismiss = { selectItem = null },
                            onSave = { requestId, requestStatus, images, description, absenceDateFrom, absenceDateTo, newImages ->
                                viewModel.editRequest(
                                    requestId = requestId,
                                    status = requestStatus,
                                    images = images,
                                    description = description,
                                    absenceDateFrom = absenceDateFrom,
                                    absenceDateTo = absenceDateTo,
                                    newImages = newImages
                                )
                                selectItem = null
                            }
                        )
                    }

                    is FetchDataState.Error -> ErrorComponent(
                        (cardState.currentState as FetchDataState.Error).message,
                        { onSelect(it.id) }, onDismiss = { viewModel.toInitialState() }
                    )

                    FetchDataState.Initial -> selectItem = null
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SecondaryColor)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SecondaryColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.your_requests),
                        color = PrimaryColor,
                        fontSize = 20.sp,
                        lineHeight = 48.sp,
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            item { Spacer(Modifier.height(10.dp)) }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(0.5f)
                            .height(90.dp)
                            .clickable { toProfile() }
                    )
                    StatusCard(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(0.165f),
                        containerColor = approvedBadgeBackground,
                        borderColor = approvedBadgeTextTint,
                        cardType = stringResource(R.string.approved),
                        amount = badgeState.confirmed
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatusCard(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(0.2f),
                        containerColor = pendingBadgeBackground,
                        borderColor = pendingBadgeTextTint,
                        cardType = stringResource(R.string.checking),
                        amount = badgeState.pending
                    )
                    StatusCard(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(0.2f),
                        containerColor = exitButtonBackground,
                        borderColor = exitButtonIconTint,
                        cardType = stringResource(R.string.rejected),
                        amount = badgeState.rejected
                    )
                }
            }

            item { Spacer(Modifier.height(20.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.recent_request),
                    color = PrimaryColor,
                    fontSize = 20.sp,
                    lineHeight = 48.sp,
                    fontFamily = Nunito,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black
                )
            }

            items(requestList) { item ->
                val parsedItem = item.copy(
                    absenceDateFrom = formatIsoDateToDisplay(item.absenceDateFrom),
                    absenceDateTo = formatIsoDateToDisplay(item.absenceDateTo),
                    createTime = formatIsoDateToDisplay(item.createTime)
                )
                RequestCard(
                    request = parsedItem,
                    onSelect = { selectItem = item }
                )
            }
        }
        FloatingActionButton(
            onClick = { toAddScreen() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 32.dp, bottom = 60.dp),
            containerColor = SecondaryButton,
            shape = RoundedCornerShape(25)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                modifier = Modifier.size(36.dp),
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}


@Composable
fun StatusCard(
    modifier: Modifier,
    containerColor: Color,
    borderColor: Color,
    cardType: String,
    amount: Int
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = modifier
            .padding(8.dp)
            .border(color = borderColor, width = 2.dp, shape = RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = cardType,
                color = borderColor,
                fontFamily = Nunito,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = amount.toString(),
                color = borderColor,
                fontFamily = Nunito,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
