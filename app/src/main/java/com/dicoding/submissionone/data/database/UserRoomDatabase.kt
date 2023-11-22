package com.dicoding.submissionone.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class UserRoomDatabase: RoomDatabase() {
    companion object {
        var INSTANCE: UserRoomDatabase? = null

        fun getInstance(context: Context): UserRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(UserRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDatabase::class.java,
                        "user"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
    abstract fun noteDao(): NoteDao
}
