package com.example.getpermissions

import android.Manifest
import android.app.Activity
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
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
            // ここでは特に何もしない
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Second Screen")
        // 権限をリクエストするボタン
        if (!checkLocationPermission(context)) {
            Button(
                onClick = {
                    // 位置情報のパーミッションをリクエストする
                    // shouldShowRequestPermissionRationale を確認する
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            context as Activity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        requestPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    } else {
                        // ダイアログを表示する
                        openDialog.value = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = "権限を許可する")
            }
        }
        Button(onClick = {
            navController.navigate("third")
        }) {
            Text("Go to Third Screen")
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