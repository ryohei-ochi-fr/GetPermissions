package com.example.getpermissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun SecondScreen(navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        //説明文
        Text(
            text = "このアプリでは、位置情報を利用します。アプリの使用を続けるには、位置情報の使用を許可してください。",
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            textAlign = TextAlign.Center
        )
        //承諾・承諾しないボタン
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            //承諾しないボタン
            Button(
                onClick = {
                    navController.navigate("fifth")
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = "承諾しない")
            }
            //承諾するボタン
            Button(
                onClick = {
                    //権限があるか確認し、ある場合はthirdへ遷移する
                    if (checkLocationPermission(context)){
                        navController.navigate("third")
                    }else{
                        navController.navigate("fifth")
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = "承諾する")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    //ダミーのNavControllerを生成
    //ここでは画面遷移できないことに注意
    SecondScreen(navController = NavController(context = androidx.compose.ui.platform.LocalContext.current))
}