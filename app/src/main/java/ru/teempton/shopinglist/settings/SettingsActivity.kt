package ru.teempton.shopinglist.settings

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.preference.PreferenceManager
import ru.teempton.shopinglist.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var defPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedTheme())
        setContentView(R.layout.activity_settings)
        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.place_holder, SettingsFragment()).commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home)finish()
        return super.onOptionsItemSelected(item)
    }
    private fun getSelectedTheme():Int{
        return if (defPreferences.getString("theme_key","blue").toString()=="blue"){
            R.style.Theme_ShopingListBlue
        }else{
            R.style.Theme_ShopingListRed
        }
    }
}

