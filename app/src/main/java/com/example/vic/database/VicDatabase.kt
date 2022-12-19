package com.example.vic.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseCustomerIndex::class], version = 1, exportSchema = false)
abstract class VicDatabase : RoomDatabase() {

    abstract val customerIndexDatabaseDao: CustomerIndexDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: VicDatabase? = null

        fun getInstance(context: Context): VicDatabase {
            var instance = INSTANCE

            if (instance != null) {
                return instance
            }

            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VicDatabase::class.java,
                        "vic_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
            }
            return instance!!
        }
    }
}
