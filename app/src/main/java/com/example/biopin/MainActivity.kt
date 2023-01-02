package com.example.biopin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.biopin.databinding.ActivityMainBinding
import com.example.biopin.fragments.MapsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        val navView : NavigationView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.logsFragment -> navController.navigate(R.id.logsFragment)
                R.id.mapsFragment ->  {
                    val fragment = MapsFragment()
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.mapsFragment, fragment)
                    fragmentTransaction.commit()
                }
                R.id.homepageFragment -> navController.navigate(R.id.homepageFragment)
                R.id.FAQFragment -> navController.navigate(R.id.FAQFragment)
                R.id.aboutFragment -> navController.navigate(R.id.aboutFragment)
                R.id.logout -> finish()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        bottomNavigationView = findViewById(R.id.bottomNavMenu)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.logsFragment -> navController.navigate(R.id.logsFragment)
                R.id.mapsFragment -> navController.navigate(R.id.mapsFragment)
                R.id.homepageFragment -> navController.navigate(R.id.homepageFragment)
                R.id.FAQFragment -> navController.navigate(R.id.FAQFragment)
                R.id.aboutFragment -> navController.navigate(R.id.aboutFragment)
            }
            true
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}