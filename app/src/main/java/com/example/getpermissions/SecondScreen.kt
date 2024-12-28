package com.example.getpermissions

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun SecondScreen(navController: NavHostController) {
    val context = LocalContext.current
    // AppSettingsDialogを表示させるかどうかを保持する
    val openDialog = remember { mutableStateOf(false) }
    // パーミッションリクエストのランチャーを作成
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            // パーミッションが許可された場合の処理
            navController.navigate("fourth_screen") {
                popUpTo("second") { inclusive = true }
            }
        } else {
            // パーミッションが拒否された場合の処理
            // todo: 一度でも権限リクエストのポップアップで「許可しない」が選択された場合「設定画面」から手動で許可する必要がある
            // todo: 「許可しない」が選択された場合の処理が必要 → デバック中はアンインストールして対応中
            // todo: ↓アンインストールコマンド
            // todo: adb uninstall com.example.getpermissions
            navController.navigate("third")
        }
    }
    LaunchedEffect(key1 = true) {
        showToast(context, getFirstTimeLaunchValue(context).toString())
        if(getFirstTimeLaunchValue(context) == 3){
            // 一度でもThirdScreenに遷移したら 3 が設定されている
            // 権限がない場合、FifthScreenに遷移する
            if (!checkLocationPermission(context)) {
                navController.navigate("fifth")
            }
        }else{
            setFirstTimeLaunchValue(context, 2)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 権限が必要な理由を表示
        Text(
            text = stringResource(R.string.permission_reason),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        // 権限をリクエストするボタン
        if (!checkLocationPermission(context)) {
            Button(
                onClick = {
                    // 位置情報のパーミッションをリクエストする
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = stringResource(R.string.request_permission))
            }
            Button(onClick = {
                navController.navigate("third")
            }) {
                Text(text = stringResource(R.string.deny_permission))
            }
        }

        // アプリ設定画面を開くダイアログ
        if (openDialog.value) {
            AlertDialog(
                title = { Text("権限が必要です") },
                text = { Text("このアプリを動作させるには、位置情報の権限が必要です。設定画面から権限を許可してください。") },
                onDismissRequest = { openDialog.value = false },
                confirmButton = {
                    Button(onClick = {
                        openAppSettings(context)
                        openDialog.value = false
                    }) {
                        Text("設定画面へ")
                    }
                },
                dismissButton = {
                    Button(onClick = { openDialog.value = false }) {
                        Text("キャンセル")
                    }
                }
            )
        }
    }
}
