package com.example.note.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.note.bean.NoteBean
import com.example.note.repository.MainRepository

class MainViewModel(context: Context) : ViewModel() {
    val noteList by lazy {
        MutableLiveData<ArrayList<NoteBean>>()
    }
    private val mRepository by lazy {
        MainRepository(context)
    }

    fun loadNote() {
        noteList.postValue(mRepository.getNoteBySql())
    }
}