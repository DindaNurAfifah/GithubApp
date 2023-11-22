package com.dicoding.submissionone.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favoriteuser")
data class Note(
    @ColumnInfo("username")
    val login: String,

    @ColumnInfo("avatar")
    val avatarUrl: String,

    @ColumnInfo("id")
    @PrimaryKey
    val id: Int
): Serializable
