package com.example.note.repository

import android.content.Context
import android.database.Cursor
import com.example.note.bean.NoteBean
import com.example.note.sql.MySQLiteOPenHelper

class MainRepository(context: Context) {
    private val noteSqLiteOPenHelper: MySQLiteOPenHelper =
        MySQLiteOPenHelper(context, "Note.db", null, 1)

    fun getNoteBySql(): ArrayList<NoteBean> {
        val noteList = ArrayList<NoteBean>()
        val db = noteSqLiteOPenHelper.readableDatabase
        val cursor: Cursor = db.query("Note", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val id: Int = cursor.getInt(cursor.getColumnIndex("id"))
                val title: String = cursor.getString(cursor.getColumnIndex("title"))
                val text: String = cursor.getString(cursor.getColumnIndex("text"))
                val time: String = cursor.getString(cursor.getColumnIndex("time"))
                noteList.add(NoteBean(id, title, text, time))
            } while (cursor.moveToNext())
        }
        cursor.close()
        noteSqLiteOPenHelper.close()
        return noteList
    }
}