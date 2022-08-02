package ru.teempton.shopinglist.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import ru.teempton.shopinglist.R
import ru.teempton.shopinglist.databinding.ActivityMainBinding
import ru.teempton.shopinglist.dialogs.NewListDialog
import ru.teempton.shopinglist.fragments.FragmentManager
import ru.teempton.shopinglist.fragments.NoteFragment
import ru.teempton.shopinglist.fragments.ShopListNamesFragment
import ru.teempton.shopinglist.settings.SettingsActivity
import ru.teempton.shopinglist.settings.SettingsFragment

class MainActivity : AppCompatActivity(),NewListDialog.Listener {
    lateinit var binding: ActivityMainBinding
    private lateinit var defPreferences: SharedPreferences
    private var currentMenuItemId = R.id.shop_list
    private var currentTheme = ""
    private var iAd:InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        defPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedTheme())
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentTheme = defPreferences.getString("theme_key", "blue").toString()
        setBottomNavListener()
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(),this)
        loadInterAd()
    }

    private fun showInterId(adListener: AdListener){
        if (iAd!=null){
            iAd?.fullScreenContentCallback = object : FullScreenContentCallback(){
                override fun onAdDismissedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                    adListener.onFinish()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    iAd = null
                    loadInterAd()
                }

                override fun onAdShowedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                }
            }
            iAd?.show(this)
        }else{
            adListener.onFinish()
        }
    }

    private fun loadInterAd(){
        val request = AdRequest.Builder().build()
        InterstitialAd.load(this,getString(R.string.inter_ad_id),request, object : InterstitialAdLoadCallback(){
            override fun onAdLoaded(ad: InterstitialAd) {
                iAd = ad
            }

            override fun onAdFailedToLoad(ad: LoadAdError) {
                iAd = null
            }
        })
    }

    private fun setBottomNavListener(){
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.settings->{
                    showInterId(object :AdListener{
                        override fun onFinish() {
                            //эта функция запуститься только когда пользователь закроет объявление
                            startActivity(Intent(this@MainActivity,SettingsActivity::class.java))
                        }

                    })

                }
                R.id.notes->{
                    currentMenuItemId = R.id.notes
                    FragmentManager.setFragment(NoteFragment.newInstance(),this)
                }
                R.id.shop_list->{
                    currentMenuItemId = R.id.shop_list
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(),this)
                }
                R.id.new_item->{
                    FragmentManager.currentFrag?.onClickNew()
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bNav.selectedItemId = currentMenuItemId
        if (defPreferences.getString("theme_key", "blue") != currentTheme) recreate()
    }


    private fun getSelectedTheme():Int{
        return if (defPreferences.getString("theme_key","blue")=="blue"){
            R.style.Theme_ShopingListBlue
        }else{
            R.style.Theme_ShopingListRed
        }
    }

    override fun onClick(name: String) {
        Log.d("MyLog",name)
    }

    interface AdListener{
        fun onFinish()
    }
}