package com.zbistapp.weatherappforavito.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zbistapp.weatherappforavito.R
import com.zbistapp.weatherappforavito.ui.main.MainFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commit()
        }
    }
}