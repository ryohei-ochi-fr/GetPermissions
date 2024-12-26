package com.example.getpermissions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.getpermissions.ui.theme.GetPermissionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetPermissionsTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                // Use LaunchedEffect inside the composable to run only one time when the screen load.
                LaunchedEffect(key1 = true) {
                    if (checkLocationPermission(context)) {
                        navController.navigate("fourth_screen") {
                            popUpTo("first") { inclusive = true }
                        }
                    } else {
                        navController.navigate("second") {
                            popUpTo("first") { inclusive = true }
                        }
                    }
                }
                NavHost(navController = navController, startDestination = "first") {
                    composable("first") { MainContent(navController) }
                    composable("second") { SecondScreen(navController) }
                    composable("third") { ThirdScreen(navController) }
                    composable("fourth_screen") { FourthScreen() }
                }
            }
        }
    }
}

@Composable
fun MainContent(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 説明文
        Text(
            text = "このアプリを実行するには、位置情報の権限が必要です。このアプリは、お使いの現在地情報を確認します。権限を許可しない場合、このアプリは使用できません。",
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
            // アプリを終了するボタン
            val context = LocalContext.current
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
}