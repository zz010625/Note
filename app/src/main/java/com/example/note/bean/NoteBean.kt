package com.example.note.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteBean(val id: Int, val title: String, val text: String, val time: String) : Parcelable