package com.example.tsunotintime.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsunotintime.R
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.approvedBadgeBackground
import com.example.tsunotintime.ui.theme.approvedBadgeTextTint
import com.example.tsunotintime.ui.theme.exitButtonBackground
import com.example.tsunotintime.ui.theme.exitButtonIconTint
import com.example.tsunotintime.ui.theme.pendingBadgeBackground
import com.example.tsunotintime.ui.theme.pendingBadgeTextTint

@Composable
fun StatusBadge(status: RequestStatus) {
    Box(
        modifier = Modifier
            .background(
                color = when (status) {
                    RequestStatus.Checking -> pendingBadgeBackground
                    RequestStatus.Confirmed -> approvedBadgeBackground
                    RequestStatus.Rejected -> exitButtonBackground
                }, shape = RoundedCornerShape(35)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (status) {
                RequestStatus.Checking -> stringResource(R.string.checking)
                RequestStatus.Confirmed -> stringResource(R.string.approved)
                RequestStatus.Rejected -> stringResource(R.string.rejected)
            },
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = when (status) {
                RequestStatus.Checking -> pendingBadgeTextTint
                RequestStatus.Confirmed -> approvedBadgeTextTint
                RequestStatus.Rejected -> exitButtonIconTint
            },
            fontFamily = Nunito
        )
    }
}