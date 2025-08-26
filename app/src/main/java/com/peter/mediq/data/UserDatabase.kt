package com.peter.mediq.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peter.mediq.model.User
import com.peter.mediq.ui.screens.newposts.EmergencyPost
import com.peter.mediq.ui.screens.newposts.EmergencyPostDao
import com.peter.mediq.data.UserDao

@Database(
    entities = [User::class, EmergencyPost::class],
    version = 3,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun emergencyPostDao(): EmergencyPostDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            // Return existing instance if available
            return INSTANCE ?: synchronized(this) {
                // Create a new instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration() // auto-wipe DB on schema change
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
