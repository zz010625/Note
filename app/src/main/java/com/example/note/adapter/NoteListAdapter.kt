package com.example.note.adapter

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note.R
import com.example.note.bean.NoteBean
import com.example.note.databinding.ItemNoteBinding
import com.example.note.sql.MySQLiteOPenHelper
import com.example.note.view.MainActivity
import com.example.note.view.NoteActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


class NoteListAdapter(private val context: Context) :
    RecyclerView.Adapter<NoteListAdapter.InnerHolder>() {
    private var mNoteList = ArrayList<NoteBean>()
    private val noteSqLiteOPenHelper by lazy {
        MySQLiteOPenHelper(context, "Note.db", null, 1)
    }

    class InnerHolder(itemView: View, val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val itemBinding = DataBindingUtil.inflate<ItemNoteBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_note,
            parent,
            false
        )
        return InnerHolder(itemBinding.root, itemBinding)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        //拿到对应位置的数据
        val itemData = mNoteList[position]
        //绑定数据 向Binding里设置数据
        holder.binding.noteBean = itemData
        //设置点击事件
        holder.binding.eventHandle = EventHandle(itemData, context)
    }

    override fun getItemCount(): Int {
        return mNoteList.size
    }

    fun setData(contentList: MutableList<NoteBean>) {
        //清空并添加新内容内容
        mNoteList.run {
            clear()
            addAll(contentList)
            notifyDataSetChanged()
        }
    }

    inner class EventHandle(private val itemData: NoteBean, private val context: Context) {
        fun onItemSingleClick(view: View) {
            //点击打开NoteActivity
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(
                "noteBean",
                NoteBean(itemData.id, itemData.title, itemData.text, itemData.time)
            )
            context.startActivity(intent)
        }

        fun onItemLongClick(view: View): Boolean {
            //长按提示是否删除
            val bottomSheetDialog = BottomSheetDialog(context)
            val bottomSheetDialogView =
                LayoutInflater.from(context).inflate(R.layout.item_delete_dialog, null)
            bottomSheetDialog.setContentView(bottomSheetDialogView)
            bottomSheetDialog.show()
            //设置删除的点击事件
            val delete = bottomSheetDialogView.findViewById<TextView>(R.id.tv_delete)
            delete.setOnClickListener {
                val db: SQLiteDatabase = noteSqLiteOPenHelper.readableDatabase
                db.delete("Note", "id = ?", arrayOf(itemData.id.toString()))
                noteSqLiteOPenHelper.close()
                bottomSheetDialog.dismiss()
                //删除后回调更新数据
                val mainActivity = context as MainActivity
                mainActivity.finishDelete()
            }
            return true
        }
    }
}