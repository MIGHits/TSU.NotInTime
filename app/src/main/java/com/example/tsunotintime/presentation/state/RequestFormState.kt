package com.example.tsunotintime.presentation.state

import android.net.Uri
import com.example.tsunotintime.AppContext.Companion.instance
import com.example.tsunotintime.R
import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class RequestFormState(
    val absenceDateFrom: String = DateTimeFormatter.ofPattern(instance.getString(R.string.isoSecondAccuracyFormat))
        .format(LocalDateTime.now(ZoneOffset.UTC)),
    val absenceDateTo: String = DateTimeFormatter.ofPattern(instance.getString(R.string.isoSecondAccuracyFormat))
        .format(LocalDateTime.now(ZoneOffset.UTC)),
    val description: String = EMPTY_RESULT,
    val images: List<Uri> = emptyList(),
    val isValid: Boolean = false
)