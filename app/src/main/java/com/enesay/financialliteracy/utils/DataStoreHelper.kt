package com.enesay.financialliteracy.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreHelper(private val context: Context) {
    companion object {
        private val Context.dataStore :DataStore<Preferences> by preferencesDataStore("user_preferences")

        val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
        val EMAIL_KEY = stringPreferencesKey("email")
        val PASSWORD_KEY = stringPreferencesKey("password")
        val USER_ID = stringPreferencesKey("user_id")
    }

    // Save user preferences
    suspend fun saveUserPreferences(rememberMe: Boolean, email: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[REMEMBER_ME_KEY] = rememberMe
            preferences[EMAIL_KEY] = email
            preferences[PASSWORD_KEY] = password
        }
    }

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }
    suspend fun clearUserId() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID) // USER_ID anahtarını kaldır
        }
    }
    // Get rememberMe state as a Flow
    val rememberMeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[REMEMBER_ME_KEY] ?: false
        }

    // Get email as a Flow
    val emailFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[EMAIL_KEY]
        }

    // Get password as a Flow
    val passwordFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PASSWORD_KEY]
        }

    val userIdFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID]
        }
}
