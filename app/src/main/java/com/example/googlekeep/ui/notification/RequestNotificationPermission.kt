package com.example.googlekeep.ui.notification

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandlePostNotificationPermission() {
    val postNotificationPermission = rememberPermissionState(permission = POST_NOTIFICATIONS)
    LaunchedEffect(Unit) {
        if (postNotificationPermission.status.isGranted.not()) {
            postNotificationPermission.launchPermissionRequest()
        }
    }
}