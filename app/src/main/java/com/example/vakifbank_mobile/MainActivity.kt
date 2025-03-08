package com.example.vakifbank_mobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.datasource.exchangedatasource.abstraction.ExchangeRatesDataSource
import com.example.ui.extensions.extension.gone
import com.example.ui.extensions.extension.visible
import com.example.util.NavOption
import com.example.vakifbank_mobile.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var datasource: ExchangeRatesDataSource

    private lateinit var navController: NavController



    private lateinit var binding: ActivityMainBinding


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }



        val menu: Menu = MenuBuilder(this)
        MenuInflater(this).inflate(com.example.ui.R.menu.bottom_navigation_menu, menu) //

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        navController = navHostFragment.navController

        navHostFragment.navController.addOnDestinationChangedListener{ _, destination,_ ->
            when (destination.id) {
                com.example.navigation.R.id.storyFragment -> {
                    binding.customBottomNavigation.gone()
                }com.example.navigation.R.id.marketFragment -> {
                    binding.customBottomNavigation.gone()
                }
                com.example.navigation.R.id.splashFragment->{
                    binding.customBottomNavigation.gone()
                }

                else -> {
                    binding.customBottomNavigation.visible()

                }
            }
        }

        binding.customBottomNavigation.customBottomNavigation?.setMenu(menu)

        binding.customBottomNavigation.customBottomNavigation?.setOnItemSelectedListener {
            when (it) {
                com.example.ui.R.id.bottom_fast_transactions -> {

                }
                com.example.ui.R.id.bottom_market_knowledge -> {
                    navController.navigate(com.example.navigation.R.id.marketFragment,null,
                        NavOption.rightAnim)
                }

                com.example.ui.R.id.bottom_calculation_tools -> {
                }

                com.example.ui.R.id.bottom_other_transactions_test -> {
                }
            }
        }




    }



}