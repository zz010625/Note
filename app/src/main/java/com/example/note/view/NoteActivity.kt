package com.example.note.view

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.note.R
import com.example.note.bean.NoteBean
import com.example.note.sql.MySQLiteOPenHelper
import com.example.note.util.TimeUtil


class NoteActivity : AppCompatActivity() {
    private lateinit var mTitleEditText: EditText
    private lateinit var mTextEditText: EditText
    private lateinit var mBackImageView: ImageView
    private lateinit var mOkImageView: ImageView
    private lateinit var mClickLayout: ConstraintLayout
    private var mNoteBean: NoteBean? = null
    private val noteSqLiteOPenHelper by lazy {
        MySQLiteOPenHelper(this, "Note.db", null, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        //初始化相关view
        initView()
        //设置相关事件
        initEvent()
    }

    private fun initView() {
        mTitleEditText = findViewById(R.id.et_title)
        mTextEditText = findViewById(R.id.et_text)
        mClickLayout = findViewById(R.id.layout_click_area)
        mBackImageView = findViewById(R.id.iv_back)
        mOkImageView = findViewById(R.id.iv_ok)

        //获取数据
        mNoteBean = intent.getParcelableExtra("noteBean")
        mTitleEditText.setText(mNoteBean?.title)
        mTextEditText.setText(mNoteBean?.text)
    }

    private fun initEvent() {
        //点击Text下方空白区域 使 mTextEditText 获取焦点并弹出软键盘
        mClickLayout.setOnClickListener {
            mTextEditText.requestFocus()
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(mTextEditText, 0)
        }

        //点击返回
        mBackImageView.setOnClickListener {
            finish()
        }

        //点击√
        mOkImageView.setOnClickListener {
            if (!TextUtils.isEmpty(mTitleEditText.text) && !TextUtils.isEmpty(mTextEditText.text)) {
                finish()
            } else {
                Toast.makeText(this, "标题和内容不能为空!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onPause() {
        if (!TextUtils.isEmpty(mTitleEditText.text) && !TextUtils.isEmpty(mTextEditText.text)) {
            val db: SQLiteDatabase = noteSqLiteOPenHelper.readableDatabase
            val values = ContentValues()
            values.put("title", mTitleEditText.text.toString())
            values.put("text", mTextEditText.text.toString())
            values.put("time", TimeUtil.getSystemTime())

            if (mNoteBean?.id == null) {
                //保存当前新建的Note数据
                db.insert("Note", null, values)
            } else {
                //保存当前修改的Note数据
                db.update("Note", values, "id = ?", arrayOf("${mNoteBean!!.id}"))
            }
            noteSqLiteOPenHelper.close()
        }
        super.onPause()
    }
}