package ru.teempton.shopinglist.db

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import ru.teempton.shopinglist.R
import ru.teempton.shopinglist.databinding.NoteListItemBinding
import ru.teempton.shopinglist.entities.NoteItem
import ru.teempton.shopinglist.utils.HTMLManager
import ru.teempton.shopinglist.utils.Manager

class NoteAdapter(private val listener: Listener, private val defPreferences: SharedPreferences): ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComparator()) {
    //создвется разметка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }
    //заполняется созданная разметка
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener, defPreferences)
    }


    class ItemHolder(view:View):RecyclerView.ViewHolder(view){
        private val binding = NoteListItemBinding.bind(view)

        fun setData(note:NoteItem, listener: Listener, defPreferences: SharedPreferences) = with(binding){
            tvTitle.text = note.title
            tvDescription.text = HTMLManager.getFromHTML(note.content).trim()
            tvTime.text = Manager.getTimeFormat(note.time, defPreferences)
            itemView.setOnClickListener {
                listener.onClickItem(note)
            }
            imDelete.setOnClickListener{
                listener.deleteItem(note.id!!)
            }
        }

        companion object{
            fun create(parent:ViewGroup):ItemHolder{
                return ItemHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.note_list_item,parent,false)
                )
            }
        }
    }
    class ItemComparator:DiffUtil.ItemCallback<NoteItem>(){
        //сравнивает равны ли элементы
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }
        //сравнивает весь контент внутри элемента
        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem==newItem
        }

    }

    interface Listener{
        fun deleteItem(id:Int)
        fun onClickItem(note: NoteItem)
    }

}