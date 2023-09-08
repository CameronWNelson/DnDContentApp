package com.example.dndcontentapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.File

// A static database manager for all activities to use
lateinit var dbManager: DatabaseManager

// Returns the instance of the database manager or creates it if there isn't one
@Synchronized fun getDbManager(context: Context): DatabaseManager {
    if (!(::dbManager.isInitialized)) dbManager = DatabaseManager(context, "spells.db")
    return dbManager
}

class DatabaseManager(private val context: Context, private val dbName: String) : SQLiteOpenHelper(context, dbName, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val dbDownloader = DatabaseDownloader(context)
        runBlocking {
            async(Dispatchers.IO) {
                dbDownloader.downloadAndSaveDatabase("http://192.168.0.193:9000/spelldb", dbName)
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    // TODO: Replace URL with permanent server URL
    fun openDatabase(): SQLiteDatabase {
        val dbPath = context.getDatabasePath(dbName).path

        // Download the db file if it isn't already present
        if (!File(dbPath).exists()) {
            val dbDownloader = DatabaseDownloader(context)
            runBlocking {
                async(Dispatchers.IO) {
                    dbDownloader.downloadAndSaveDatabase("http://192.168.0.193:9000/spelldb", dbName)
                }
            }
        }
        return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE)
    }

    // Inserts data into the specified table
    // Data must already be formatted for an SQL statement
    fun insert(table: String, data: String) {
        writableDatabase.execSQL("INSERT INTO $table VALUES($data)")
    }

    // Inserts data into the specified columns and table
    // Data must already be formatted for an SQL statement
    // columns must be comma separated
    fun insertSelective(table: String, columns: String, data: String) {
        writableDatabase.execSQL("INSERT INTO $table ($columns) VALUES($data)")
    }
}