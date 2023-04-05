package com.android.fiiapp.data.source.local

import android.content.Context
import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LocalDataSource<T> (
    val context: Context,
    val key: String,
    val dbname: String = "com.android.fiiapp.localdb"
) {

    inline fun <reified T> read(): T? {
        return try {
            val db = context.getSharedPreferences(dbname, Context.MODE_PRIVATE)
            val s = db.getString(key, null)
            val format = Json { ignoreUnknownKeys = true }
            format.decodeFromString<T>(s!!)
        } catch (ex: Exception) {
            Log.e(
                "LocalDataSource::read",
                "Error reading from local storage: $ex")
            null
        }
    }

    inline fun <reified T> write(i: T) {
        val format = Json { ignoreUnknownKeys = true }
        val db = context.getSharedPreferences(dbname, Context.MODE_PRIVATE)
        with (db.edit()) {
            putString(key, format.encodeToString(i))
            apply()
        }
    }
}