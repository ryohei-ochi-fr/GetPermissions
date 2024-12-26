package com.example.getpermissions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

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
fun FifthScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Fifth Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun FifthScreenPreview() {
    FifthScreen()
}