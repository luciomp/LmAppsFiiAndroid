package com.android.fiiapp.data.source.remote

import android.util.Log
import com.android.fiiapp.data.Fii
import com.android.fiiapp.data.FiiList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.zip.GZIPInputStream

class FiiRemoteDataSource(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun fetch(): FiiList = withContext(ioDispatcher) {
        Log.d("FiiRemoteDataSource", "fetch")
        try {
            var url = URL("http://app1-prod.sa-east-1.elasticbeanstalk.com/fii").openConnection()
            url.setRequestProperty("Authorization", "42399549-e526-48ab-bd46-126f83f04a4c")
            url.setRequestProperty("Content-Type", "application/json")
            url.connect()
            val reader = if (url.contentEncoding == "gzip") {
                InputStreamReader(GZIPInputStream(url.getInputStream()))
            } else {
                InputStreamReader(url.getInputStream())
            }
            val format = Json { ignoreUnknownKeys = true }
            return@withContext format.decodeFromString<FiiList>(reader.readText())
        } catch (e: SerializationException) {
            Log.e("SERIAL ERROR", e.toString())
        } catch (e: Exception) {
            Log.e("REMOTE ERROR", e.toString())
        }
        throw Exception("Unable to fetch remote repository")
    }
}