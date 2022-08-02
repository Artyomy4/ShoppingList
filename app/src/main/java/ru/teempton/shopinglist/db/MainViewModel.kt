package ru.teempton.shopinglist.db

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.teempton.shopinglist.entities.LibraryItem
import ru.teempton.shopinglist.entities.NoteItem
import ru.teempton.shopinglist.entities.ShopListItem
import ru.teempton.shopinglist.entities.ShopListNameItem

class MainViewModel(database:MainDataBase):ViewModel() {
    val dao = database.getDao()

    val libraryItems = MutableLiveData<List<LibraryItem>>()

    val allNotes:LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allShopListNames:LiveData<List<ShopListNameItem>> = dao.getAllShopListNames().asLiveData()

    fun getAllItemsFromList(listId:Int):LiveData<List<ShopListItem>>{
        return dao.getAllShopListItems(listId).asLiveData()
    }

    fun getAllLibraryItems(name: String) = viewModelScope.launch{
        libraryItems.postValue(dao.getAllLibraryItems(name))
    }

    fun insertNote(note:NoteItem) = viewModelScope.launch { dao.insertNote(note)}

    fun insertShoppingListName(listName:ShopListNameItem) = viewModelScope.launch { dao.insertShoppingListName(listName)}

    fun insertShopItem(shopListItem: ShopListItem) = viewModelScope.launch {
        dao.insertShopItem(shopListItem)
        if (!isLibraryItemExist(shopListItem.name))dao.insertLibraryItem(LibraryItem(null,shopListItem.name))
    }

    fun updateNote(note:NoteItem) = viewModelScope.launch { dao.updateNote(note)}
    fun updateLibraryItem(item: LibraryItem) = viewModelScope.launch { dao.updateLibraryItem(item)}
    fun updateShopListName(shopListName: ShopListNameItem) = viewModelScope.launch { dao.updateShopListName(shopListName)}

    fun updateListItem(shopListItem: ShopListItem) = viewModelScope.launch { dao.updateListItem(shopListItem)}

    fun deleteNote(id:Int) = viewModelScope.launch { dao.deleteNote(id)}
    fun deleteLibraryItem(id:Int) = viewModelScope.launch { dao.deleteLibraryItem(id)}
    fun deleteShopList(id:Int, deleteList:Boolean) = viewModelScope.launch {
        if (deleteList)dao.deleteShopListName(id)
        dao.deleteShopListItemsByListId(id)
    }

    private suspend fun isLibraryItemExist(name:String):Boolean{
        return dao.getAllLibraryItems(name).isNotEmpty()
    }

    class MainViewModelFactory(val database: MainDataBase): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }


    }

}