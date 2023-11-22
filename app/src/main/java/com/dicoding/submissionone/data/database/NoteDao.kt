package com.dicoding.submissionone.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import kotlin.Int
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Query("SELECT * FROM favoriteuser")
    fun loadAll(): LiveData<List<Note>>

    @Insert
    fun insert(favoriteuser: Note)

    @Query("DELETE FROM favoriteuser WHERE favoriteuser.id = :id")
    fun remove(id: Int): Int

    @Query("SELECT count(*) FROM favoriteuser WHERE favoriteuser.id = :id")
    fun checkFavorite(id: Int): Int
}
