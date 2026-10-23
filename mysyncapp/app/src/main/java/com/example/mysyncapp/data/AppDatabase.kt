package com.example.mysyncapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mysyncapp.data.dao.SyncItemDao
import com.example.mysyncapp.data.entity.SyncItem

@Database(entities = [SyncItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun syncItemDao(): SyncItemDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        /*Context es una clase fundamental en Android que representa el entorno de ejecución
        de tu aplicación.Piensa en él como un “objeto que sabe todo sobre la aplicación”.*/
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mysync_db"
                ).build()
                INSTANCE = inst
                inst
            }
        }//end getInstance
    }
}
