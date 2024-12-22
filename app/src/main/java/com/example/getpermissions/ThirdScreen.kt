package com.example.getpermissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ThirdScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        //説明文
        Text(
            text = "このアプリは、あなたのマイクを使って音声をスピーカーで再生します。",
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            textAlign = TextAlign.Center
        )
        //スタートボタン
        Button(
            onClick = {
                navController.navigate("fourth")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "スタート")
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