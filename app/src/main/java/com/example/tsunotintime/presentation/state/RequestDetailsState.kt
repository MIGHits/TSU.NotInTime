package com.example.tsunotintime.presentation.state

import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.domain.entity.RequestModel

data class RequestDetailsState(
    val isEditing: Boolean = false,
    val requestModel: RequestModel? = RequestModel(
        id = EMPTY_RESULT,
        createTime = EMPTY_RESULT,
        status = RequestStatus.Checking,
        firstName = EMPTY_RESULT,
        middleName = EMPTY_RESULT,
        lastName = EMPTY_RESULT,
        checkerUsername = EMPTY_RESULT,
        description = EMPTY_RESULT,
        images = emptyList(),
        userId = EMPTY_RESULT,
        absenceDateTo = EMPTY_RESULT,
        absenceDateFrom = EMPTY_RESULT
    ),
    val errorMessage: String = EMPTY_RESULT
)