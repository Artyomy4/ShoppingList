package ru.teempton.shopinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.teempton.shopinglist.entities.LibraryItem
import ru.teempton.shopinglist.entities.NoteItem
import ru.teempton.shopinglist.entities.ShopListNameItem
import ru.teempton.shopinglist.entities.ShopListItem

@Dao
interface Dao {

    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM shopping_list_names")
    fun getAllShopListNames(): Flow<List<ShopListNameItem>>

    @Query("SELECT * FROM shop_list_item WHERE listId = :listId")
    fun getAllShopListItems(listId:Int): Flow<List<ShopListItem>>

    @Query("SELECT * FROM library WHERE name LIKE :name")
    suspend fun getAllLibraryItems(name:String): List<LibraryItem>

    @Query("DELETE FROM note_list WHERE id is :id")
    suspend fun deleteNote(id:Int)
    @Query("DELETE FROM shopping_list_names WHERE id is :id")
    suspend fun deleteShopListName(id:Int)

    @Query("DELETE FROM shop_list_item WHERE listId = :listId")
    suspend fun deleteShopListItemsByListId(listId:Int)

    @Query("DELETE FROM library WHERE id = :id")
    suspend fun deleteLibraryItem(id:Int)

    @Insert
    suspend fun insertNote(note:NoteItem)

    @Insert
    suspend fun insertShoppingListName(name:ShopListNameItem)

    @Insert
    suspend fun insertLibraryItem(libraryItem:LibraryItem)

    @Insert
    suspend fun insertShopItem(shopListItem:ShopListItem)

    @Update
    suspend fun updateNote(note:NoteItem)

    @Update
    suspend fun updateLibraryItem(item:LibraryItem)
    @Update
    suspend fun updateListItem(shopListItem:ShopListItem)
    @Update
    suspend fun updateShopListName(shopListName:ShopListNameItem)
}