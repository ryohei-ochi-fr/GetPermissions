package com.example.getpermissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import java.io.File

@Composable
fun FifthScreen(navController: NavController) {
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
            navController.navigate("sixth")
        } else {
            // パーミッションが拒否された場合の処理
            // ここでは特に何もしない
        }
    }
    // キャッシュを削除する
    LaunchedEffect(key1 = true) {
        clearAppCache(context)
        // 権限がある場合 sixth へ遷移する
        if (checkLocationPermission(context)) {
            navController.navigate("sixth")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 説明文
        Text(
            text = "このアプリを実行するには、位置情報の権限が必要です。このアプリは、マイクを通してスピーカーから音声を再生するために、お使いの現在地情報を確認します。権限を許可しない場合、このアプリは使用できません。",
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            textAlign = TextAlign.Center
        )
        // ボタン群
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 権限をリクエストするボタン
            Button(
                onClick = {
                    // 位置情報のパーミッションをリクエストする
                    if (
                        !checkLocationPermission(context)
                    ) {
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
                    } else {
                        navController.navigate("sixth")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = "権限を許可する")
            }
            // アプリを終了するボタン
            Button(
                onClick = {
                    finishApp(context)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "アプリを終了する")
            }
        }
    }
    // アプリ設定画面を開くダイアログ
    if (openDialog.value) {
        AppSettingsDialog(
            onDismissRequest = { openDialog.value = false },
            onConfirmation = {
                openAppSettings(context)
                openDialog.value = false
            }
        )
    }
}

// アプリ設定画面を開くダイアログ
@Composable
fun AppSettingsDialog(onDismissRequest: () -> Unit, onConfirmation: () -> Unit) {
    AlertDialog(
        title = { Text("権限が必要です") },
        text = { Text("このアプリを動作させるには、位置情報の権限が必要です。設定画面から権限を許可してください。") },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onConfirmation) {
                Text("設定画面へ")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("キャンセル")
            }
        }
    )
}

// 設定画面を開く
fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

// アプリのキャッシュを削除する関数
fun clearAppCache(context: Context) {
    try {
        val cacheDir = context.cacheDir
        if (cacheDir.isDirectory) {
            deleteDir(cacheDir)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// ディレクトリを再帰的に削除する関数
fun deleteDir(dir: File): Boolean {
    if (dir.isDirectory) {
        val children = dir.list()
        for (i in children.indices) {
            val success = deleteDir(File(dir, children[i]))
            if (!success) {
                return false
            }
        }
    }
    return dir.delete()
}

fun finishApp(context: Context) {
    val activity = context as? Activity
    activity?.finish()
}

@Preview(showBackground = true)
@Composable
fun FifthScreenPreview() {
    // ダミーの NavController を生成
    // ここでは画面遷移できないことに注意
    FifthScreen(
        navController = NavController(context = androidx.compose.ui.platform.LocalContext.current)
    )
}