package com.example.getpermissions

import android.content.Context
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.getpermissions.ui.theme.GetPermissionsTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException


// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
// DataStoreのキーを定義
val isFirstTimeLaunch = booleanPreferencesKey("is_first_time_launch")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetPermissionsTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                // 画面読み込み時に一度だけ実行
                LaunchedEffect(key1 = Unit) {
                    if(!getFirstTimeLaunchValue()){
                        setFirstTimeLaunchValue(true)
                    }
                    if (checkLocationPermission(context)) {
                        navController.navigate("fourth_screen") {
                            popUpTo("first") { inclusive = true }
                        }
                    } else {
                        // SecondScreenではなく、MainContentを表示
                        navController.navigate("first") {
                            popUpTo("first") { inclusive = true }
                        }
                    }
                }
                NavHost(navController = navController, startDestination = "first") {
                    composable("first") { MainContent(navController) }
                    composable("second") { SecondScreen(navController) }
                    //composable("third") { ThirdScreen(navController) }
                    composable("third") { ThirdScreen(navController = navController) }
                    composable("fourth_screen") { FourthScreen() }
                    composable("fifth") { FifthScreen() }
                }
            }
        }
    }

    suspend fun setFirstTimeLaunchValue(value: Boolean) {
        applicationContext.dataStore.edit { settings ->
            settings[isFirstTimeLaunch] = value
        }
    }

    suspend fun getFirstTimeLaunchValue(): Boolean {
        val isFirstTimeLaunchFlow: Flow<Boolean> = applicationContext.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                // No type safety.
                preferences[isFirstTimeLaunch] ?: false
            }

        return isFirstTimeLaunchFlow.first()
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
            text = stringResource(R.string.main_content_description),
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
            // SecondScreenへ遷移するボタン
            Button(
                onClick = {
                    navController.navigate("second")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.next_button))
            }
            // アプリを終了するボタン
            val context = LocalContext.current
            Button(
                onClick = {
                    finishApp(context)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.exit_app))
            }
        }
    }
}