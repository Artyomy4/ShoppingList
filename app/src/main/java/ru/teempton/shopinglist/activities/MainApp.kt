package ru.teempton.shopinglist.activities

import android.app.Application
import ru.teempton.shopinglist.db.MainDataBase

class MainApp:Application() {
    val database by lazy { MainDataBase.getDataBase(this) }
}