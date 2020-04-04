package com.big.imageloader.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.big.imageloader.MyApplication
import com.big.imageloader.R
import com.big.imageloader.di.component.DaggerActivityComponent
import com.big.imageloader.di.module.ActivityModule
import com.big.imageloader.ui.fragment.DashboardFragment
import com.big.imageloader.ui.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val fragment1: Fragment = HomeFragment()
    val fragment2: Fragment = DashboardFragment()
    val fm: FragmentManager = supportFragmentManager
    var active = fragment1

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        fm.beginTransaction().add(R.id.main_container, fragment2, "DashboardFragment")
            .hide(fragment2).commit()

        fm.beginTransaction().add(R.id.main_container, fragment1, "HomeFragment").commit()
    }


    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {

                when (item.itemId) {
                    R.id.navigation_home -> {
                        fm.beginTransaction().hide(active).show(fragment1).commit()
                        active = fragment1
                        return true
                    }

                    R.id.navigation_dashboard -> {
                        fm.beginTransaction().hide(active).show(fragment2).commit()
                        active = fragment2
                        return true
                    }
                }
                return false
            }
        }

    private fun setUpDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MyApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }
}
