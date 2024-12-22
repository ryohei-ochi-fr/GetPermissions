package com.example.getpermissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
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
                // ナビゲーションの状態を管理するコントローラーを生成
                val navController = rememberNavController()
                // ナビゲーションホストを設定
                NavHost(navController = navController, startDestination = "first") {
                    // "first" という名前のルートに FirstScreen を紐付ける
                    composable("first") { FirstScreen(navController) }
                    // "second" という名前のルートに SecondScreen を紐付ける
                    composable("second") {
                        SecondScreen(navController)
                    }
                    // "third" という名前のルートに ThirdScreen を紐付ける
                    composable("third") { ThirdScreen(navController) }
                    // "fourth" という名前のルートに FourthScreen を紐付ける
                    composable("fourth") { FourthScreen() }
                    // "fifth" という名前のルートに FifthScreen を紐付ける
                    composable("fifth") { FifthScreen(navController) }
                    // "sixth" という名前のルートに SixthScreen を紐付ける
                    composable("sixth") { SixthScreen(navController) }
                }
            }
        }
    }
}
//権限があるか確認する
fun checkLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

@Composable
fun FirstScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("second") }) {
            Text("Go to Second Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FirstScreenPreview() {
    GetPermissionsTheme {
        val navController = rememberNavController()
        FirstScreen(navController)
    }
}