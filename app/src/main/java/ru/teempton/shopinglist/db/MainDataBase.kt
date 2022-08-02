package ru.teempton.shopinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.teempton.shopinglist.entities.LibraryItem
import ru.teempton.shopinglist.entities.NoteItem
import ru.teempton.shopinglist.entities.ShopListNameItem
import ru.teempton.shopinglist.entities.ShopListItem


@Database(entities = [LibraryItem::class,NoteItem::class,ShopListItem::class,ShopListNameItem::class], version = 1)
abstract class MainDataBase: RoomDatabase() {

    abstract fun getDao():ru.teempton.shopinglist.db.Dao

    companion object{
        @Volatile
        private var INSTANCE: MainDataBase? = null

        fun getDataBase(context: Context):MainDataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "shopping_list.db"
                ).build()
                instance
            }
        }
    }
}