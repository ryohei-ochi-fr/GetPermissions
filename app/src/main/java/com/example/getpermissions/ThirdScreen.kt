package com.example.getpermissions

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ThirdScreen(navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        //説明文
        Text(
            text = "ThirdScreen:このアプリは、あなたの位置情報を取得します。",
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            textAlign = TextAlign.Center
        )
        LaunchedEffect(key1 = true) {
            // 権限を拒否されたことを記録する
            showToast(context, getFirstTimeLaunchValue(context).toString())
            setFirstTimeLaunchValue(context, 3)
        }
        //スタートボタン
        Button(
            onClick = {
                navController.navigate("second")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "戻る")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdScreenPreview() {
    //ダミーのNavControllerを生成
    //ここでは画面遷移できないことに注意
    ThirdScreen(navController = NavController(context = LocalContext.current))
}