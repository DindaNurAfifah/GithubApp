package com.dicoding.submissionone.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.submissionone.data.database.Note
import com.dicoding.submissionone.data.database.NoteDao
import com.dicoding.submissionone.data.database.UserRoomDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val _note = MutableLiveData<List<Note>>()
    private var userDao: NoteDao?
    private var userDb: UserRoomDatabase?
    val note: LiveData<List<Note>> = _note

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        userDb = UserRoomDatabase.getInstance(application)
        userDao = userDb?.noteDao()
    }

    fun loadAll(){
        userDao?.loadAll()?.observeForever { note ->
            _note.value = note
        }
    }
}
