package com.example.tsunotintime

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.tsunotintime.AppContext.Companion.instance
import com.example.tsunotintime.common.URL.BASE_URL
import com.example.tsunotintime.common.URL.REASON
import com.example.tsunotintime.data.models.RequestModel
import com.example.tsunotintime.data.models.RequestShortModel
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.presentation.screen.NavigationScreen
import com.example.tsunotintime.ui.theme.Nunito
import com.example.tsunotintime.ui.theme.PrimaryColor
import com.example.tsunotintime.ui.theme.SecondaryColor
import com.example.tsunotintime.ui.theme.TSUNotInTimeTheme
import com.example.tsunotintime.ui.theme.approvedBadgeBackground
import com.example.tsunotintime.ui.theme.approvedBadgeTextTint
import com.example.tsunotintime.ui.theme.exitButtonBackground
import com.example.tsunotintime.ui.theme.exitButtonIconTint
import com.example.tsunotintime.ui.theme.pendingBadgeBackground
import com.example.tsunotintime.ui.theme.pendingBadgeTextTint

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
                    FullRequestCard(
                        RequestModel(
                            "asda",
                            "11.03.2025",
                            firstName = "Игорь",
                            lastName = "Мяков",
                            middleName = "Николаевич",
                            status = RequestStatus.Rejected,
                            absenceDateFrom = "11.03.2025",
                            absenceDateTo = "13.03.2025",
                            description = "ZZZZZZZZZZZZZZZ",
                            reasonId = "",
                            userId = "",
                            checkerUsername = "Политова Анастасия Михайловна",
                            images = null
                        )
                    )
                    /*NavigationScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(color = SecondaryColor), navController = navController
                    )*/
                }
            }
        }
    }
}


@Composable
fun RequestCard(request: RequestShortModel) {
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.date_period),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Medium
                )
                StatusBadge(request.status)
            }
            Text(
                "${request.absenceDateFrom} - ${request.absenceDateTo}",
                fontFamily = Nunito,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor
            )

            HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoColumn(stringResource(R.string.create_time), request.createTime)
                InfoColumn(stringResource(R.string.initials), request.username ?: "")
            }
        }
    }
}


@Composable
fun FullRequestCard(request: RequestModel) {
    val username = request.lastName + " " + request.firstName + " " + request.middleName
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
                horizontalArrangement = Arrangement.End
            ) {
                StatusBadge(request.status)
            }
            InfoColumn(stringResource(R.string.initials), username)
            Text(
                stringResource(R.string.date_period),
                fontSize = 12.sp,
                color = Color.Gray,
                fontFamily = Nunito,
                fontWeight = FontWeight.Medium
            )
            Text(
                "${request.absenceDateFrom} - ${request.absenceDateTo}",
                fontFamily = Nunito,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor
            )
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
            InfoColumn(stringResource(R.string.create_time), request.createTime)
            InfoColumn("Вердикт выставлен", request.checkerUsername)
            InfoColumn("Описание", request.description)
            ImageLink("static/images/reasons/4ae78f25-5388-4ebd-ab2f-b96acb2f647d.png")
        }
    }
}

@Composable
fun ImageLink(imageUrl: String) {
    val context = LocalContext.current

    ClickableText(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("imageUrl")
            }
        },
        onClick = {
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(REASON + imageUrl)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            } catch (_: Exception) {

            }
        },
        modifier = Modifier.padding(8.dp)
    )
}

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

@Composable
fun InfoColumn(label: String, value: String) {
    Column {
        Text(
            label,
            fontSize = 12.sp,
            color = Color.Gray,
            fontFamily = Nunito,
            fontWeight = FontWeight.Medium
        )
        Text(value, fontSize = 14.sp, fontFamily = Nunito, fontWeight = FontWeight.Bold)
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
    }
}


