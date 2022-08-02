package ru.teempton.shopinglist.db

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import ru.teempton.shopinglist.R
import ru.teempton.shopinglist.databinding.ListNameItemBinding
import ru.teempton.shopinglist.entities.ShopListNameItem

class ShopListNameAdapter(private val listener: Listener): ListAdapter<ShopListNameItem, ShopListNameAdapter.ItemHolder>(ItemComparator()) {
    //создвется разметка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }
    //заполняется созданная разметка
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position),listener)
    }


    class ItemHolder(view:View):RecyclerView.ViewHolder(view){
        private val binding = ListNameItemBinding.bind(view)

        fun setData(shopListNameItem:ShopListNameItem, listener: Listener) = with(binding){
            tvListName.text = shopListNameItem.name
            tvTime.text = shopListNameItem.time
            pBar.max = shopListNameItem.allItemCounter
            pBar.progress = shopListNameItem.checkedItemsCounter
            val counterText = "${shopListNameItem.checkedItemsCounter}/${shopListNameItem.allItemCounter}"
            tvCounter.text = counterText
            val colorState = ColorStateList.valueOf(getProgressColorState(shopListNameItem,binding.root.context))
            pBar.progressTintList = colorState
            counterCard.backgroundTintList = colorState
            itemView.setOnClickListener {
                listener.onClickItem(shopListNameItem)
            }
            imDelete.setOnClickListener{
                listener.deleteItem(shopListNameItem.id!!)
            }
            imEdit.setOnClickListener{
                listener.editItem(shopListNameItem)
            }
        }

        private fun getProgressColorState(item:ShopListNameItem,context:Context):Int{
            return if (item.checkedItemsCounter==item.allItemCounter){
                ContextCompat.getColor(context,R.color.red_main)
            }else {
                ContextCompat.getColor(context,R.color.red_main)
            }
        }

        companion object{
            fun create(parent:ViewGroup):ItemHolder{
                return ItemHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.list_name_item,parent,false)
                )
            }
        }
    }
    class ItemComparator:DiffUtil.ItemCallback<ShopListNameItem>(){
        //сравнивает равны ли элементы
        override fun areItemsTheSame(oldItem: ShopListNameItem, newItem: ShopListNameItem): Boolean {
            return oldItem.id == newItem.id
        }
        //сравнивает весь контент внутри элемента
        override fun areContentsTheSame(oldItem: ShopListNameItem, newItem: ShopListNameItem): Boolean {
            return oldItem==newItem
        }

    }

    interface Listener{
        fun deleteItem(id:Int)
        fun editItem(shopListName:ShopListNameItem)
        fun onClickItem(shopListName: ShopListNameItem)
    }

}