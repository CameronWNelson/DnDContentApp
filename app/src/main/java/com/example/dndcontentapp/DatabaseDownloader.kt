package com.example.dndcontentapp

import android.content.Context
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class DatabaseDownloader(private val context: Context) {
    fun downloadAndSaveDatabase(url: String, dbName: String) {

        val dbPath = context.getDatabasePath(dbName).path
        try {
            // Connect to the server and retrieve the db file
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val outputStream = FileOutputStream(dbPath)

                val buffer = ByteArray(4096)
                var bytesRead: Int

                // Save the downloaded database to a local file
                try {
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    outputStream.close()
                    inputStream.close()
                }
            }
            Log.d("DOGGO", "Connection closed")
            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}