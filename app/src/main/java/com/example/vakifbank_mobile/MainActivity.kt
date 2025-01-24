package com.example.vakifbank_mobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater

import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datasource.exchangedataSource.abstraction.ExchangeRatesDataSource
import com.example.ui.customviews.CustomBottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var datasource: ExchangeRatesDataSource





    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val customView = findViewById<CustomBottomNavigationView>(R.id.tata)


        val menu: Menu = MenuBuilder(this)
        MenuInflater(this).inflate(com.example.ui.R.menu.bottom_navigation_menu, menu) //

        customView.customBottomNavigation?.setMenu(menu)

        customView.customBottomNavigation?.setOnItemSelectedListener {
            when (it) {
                com.example.ui.R.id.bottom_fast_transactions -> {
                }
                com.example.ui.R.id.bottom_market_knowledge -> {
                }

                com.example.ui.R.id.bottom_calculation_tools -> {
                }

                com.example.ui.R.id.bottom_other_transactions_test -> {
                }
            }
        }



        runBlocking {
            datasource.getExchangeRatesData()
        }
    }



}