package com.example.note.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.note.R
import com.example.note.adapter.NoteListAdapter
import com.example.note.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), DeleteEvent {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAddNoteButton: FloatingActionButton
    private lateinit var mStaggeredGridLayoutManager: StaggeredGridLayoutManager
    private val mAdapter by lazy {
        NoteListAdapter(this)
    }
    private val mViewModel by lazy {
        MainViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初始化相关view
        initView()
        //观察ViewModel中数据变化
        observeData()
        //设置相关事件
        initEvent()
        //开始去加载数据
        startLoadData()
    }

    private fun initView() {
        mRecyclerView = findViewById(R.id.rv_note)
        mAddNoteButton = findViewById(R.id.fab_add)
        mStaggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRecyclerView.itemAnimator = null
        mStaggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        mRecyclerView.layoutManager = mStaggeredGridLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    private fun observeData() {
        mViewModel.noteList.observe(this, Observer {
            mAdapter.setData(it)
        })
    }

    private fun initEvent() {
        mAddNoteButton.setOnClickListener {
            //跳转至NoteActivity新建Note
            val intent = Intent(this@MainActivity, NoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startLoadData() {
        mViewModel.loadNote()
    }

    override fun onResume() {
        //从NoteActivity中编辑完后返回该Activity时 重新加载数据
        mViewModel.loadNote()
        super.onResume()
    }

    override fun finishDelete() {
        //完成删除后回调刷新数据
        mViewModel.loadNote()
    }
}