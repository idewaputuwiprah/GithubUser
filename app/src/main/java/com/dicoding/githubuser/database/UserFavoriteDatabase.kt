package com.dicoding.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserFavorite::class], version = 1)
abstract class UserFavoriteDatabase: RoomDatabase() {
    abstract fun userFavoriteDao(): UserFavoriteDao
    companion object {
        @Volatile
        private var INSTANCE: UserFavoriteDatabase? = null

        fun getDatabase(context: Context): UserFavoriteDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(lock = this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserFavoriteDatabase::class.java,
                    "user_favorite_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}