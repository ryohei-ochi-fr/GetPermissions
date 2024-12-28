package com.example.getpermissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
// DataStoreのキーを定義
val isFirstTimeLaunch = intPreferencesKey("is_first_time_launch")

fun checkLocationPermission(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        setData(Uri.fromParts("package", context.packageName, null))
    }
    context.startActivity(intent)
}

/*
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

 */

fun finishApp(context: Context){
    val activity = context as? Activity
    activity?.finish()
}


fun showToast(context: Context, message: String) {
    // デバッグ用のトースト表示
    // Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

suspend fun setFirstTimeLaunchValue(context: Context, value: Int) {
    context.dataStore.edit { settings ->
        settings[isFirstTimeLaunch] = value
    }
}
suspend fun getFirstTimeLaunchValue(context: Context): Int {
    val isFirstTimeLaunchFlow: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // No type safety.
            preferences[isFirstTimeLaunch] ?: 0
        }

    return isFirstTimeLaunchFlow.first()
}