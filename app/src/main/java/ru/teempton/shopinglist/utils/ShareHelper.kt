package ru.teempton.shopinglist.utils

import android.content.Intent
import ru.teempton.shopinglist.entities.ShopListItem

object ShareHelper {
    fun shareShopList(shopList:List<ShopListItem>,listName:String):Intent{
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeShareText(shopList,listName))
        }
        return intent
    }

    private fun makeShareText(shopList:List<ShopListItem>,listName: String):String{
        val sBuild = StringBuilder()
        sBuild.append("<<${listName}>>")
        sBuild.append("\n")
        var counter = 0
        shopList.forEach {
            sBuild.append("${++counter} - ${it.name} (${it.itemInfo})")
            sBuild.append("\n")
        }
        return sBuild.toString()
    }
}