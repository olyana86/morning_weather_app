package com.example.morningapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Settings::class],
    version = MorningDatabase.DB_VERSION,
    exportSchema = false
)
abstract class MorningDatabase : RoomDatabase() {
    abstract fun settingsDao(): SettingsDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "app.db"

        @Volatile
        private var INSTANCE: MorningDatabase? = null

        fun getInstance(context: Context): MorningDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context)
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MorningDatabase::class.java, DB_NAME
            ).build()
    }
}