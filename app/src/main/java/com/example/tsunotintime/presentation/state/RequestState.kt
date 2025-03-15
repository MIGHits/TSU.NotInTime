package com.example.tsunotintime.presentation.state

import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import com.example.tsunotintime.data.models.RequestShortModel

data class RequestState(
    val requests: List<RequestShortModel>? = emptyList(),
    val requestDetails: RequestDetailsState = RequestDetailsState(),
    val badgeState: BadgeState = BadgeState()
)