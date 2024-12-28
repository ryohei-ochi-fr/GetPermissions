package com.example.getpermissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

// パーミッションが拒否された場合の処理
// todo: 一度でも権限リクエストのポップアップで「許可しない」が選択された場合「設定画面」から手動で許可する必要がある
// todo: 「許可しない」が選択された場合の処理が必要 → 開発中はアンインストールして対応中
// todo: ↓ 開発時のアンインストールコマンド
// todo: adb uninstall com.example.getpermissions
// todo:
// todo: しっかりと権限が必要な理由を説明して設定画面から許可してもらう
// todo: 誤操作で許可されなかったならば、再インストールを勧める
// todo: バグかもしれないので、Webフォームへ誘導する

@Composable
fun FifthScreen(navController: NavController) {
    val localContext = LocalContext.current
    var hasPermission by remember { mutableStateOf(checkLocationPermission(localContext)) }

    LaunchedEffect(key1 = Unit) {
        while (!hasPermission) {
            delay(1000) // 1秒待機
        hasPermission = checkLocationPermission(localContext)
    }
        if (hasPermission) {
            navController.navigate("sixth_screen") {
                popUpTo("fifth") { inclusive = true }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 説明文
        Text(
            text = stringResource(R.string.fifth_screen_description),
            textAlign = TextAlign.Center
        )
        // スペーサー
        Spacer(modifier = Modifier.height(32.dp))
        // 権限設定画面を開くボタン
        Button(
            onClick = {
                openAppSettings(localContext)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.open_app_settings))
        }
    }
}