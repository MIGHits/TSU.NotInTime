package com.example.tsunotintime.presentation.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tsunotintime.R
import com.example.tsunotintime.ui.theme.SecondaryButton

@Composable
fun PickImage(onImagePicked: (Uri?) -> Unit, context: Context) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        onImagePicked(uri)
    }

    IconButton(onClick = { launcher.launch(context.getString(R.string.image_input)) }) {
        Icon(
            painterResource(R.drawable.attachment),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = SecondaryButton
        )
    }
}