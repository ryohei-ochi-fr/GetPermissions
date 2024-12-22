package com.example.getpermissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SixthScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //お礼文
        Text(
            text = "権限を許可していただき、ありがとうございます。\nアプリをお楽しみください。",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
        // ThirdScreenへ遷移するボタン
        Button(
            onClick = { navController.navigate("third") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "アプリを実行する")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SixthScreenPreview() {
    SixthScreen(navController = NavController(context = androidx.compose.ui.platform.LocalContext.current))
}