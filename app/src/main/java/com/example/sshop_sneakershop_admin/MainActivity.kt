package com.example.sshop_sneakershop_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sshop_sneakershop_admin.Account.AccountListActivity
import com.example.sshop_sneakershop_admin.Product.ProductListAcitivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigationrail.NavigationRailView

class MainActivity : AppCompatActivity() {

    private lateinit var topAppBar: MaterialToolbar
    private lateinit var navigationRail: NavigationRailView
    private var isExpanded: Boolean  = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        topAppBar = findViewById(R.id.main_list_toolbar)
        navigationRail = findViewById(R.id.main_navigationRail)

        navigationRail.visibility = View.GONE

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            Toast.makeText(this, "Back pressed", Toast.LENGTH_SHORT).show()
            if (isExpanded){
                turnOffNavigationRail()
            }
            else{
                turnOnNavigationRail()
            }
        }

        navigationRail.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    // Respond to navigation item 1 reselection
                    topAppBar.setTitle("Home")
                    turnOffNavigationRail()
                    true
                }
                R.id.manage_accounts -> {
                    // Respond to navigation item 2 reselection
                    topAppBar.setTitle("Accounts Management")
                    turnOffNavigationRail()
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, AccountListActivity()).commit()
                    true
                }
                R.id.manage_products->{
                    navigationRail.visibility = View.GONE
                    Intent(this, ProductListAcitivity::class.java).also {
                        startActivity(it)
                    }
                    true
                }
                else -> false
            }
        }
    }
    fun turnOnNavigationRail(){
        topAppBar.setNavigationIcon(R.drawable.ic_baseline_close_24)
        navigationRail.visibility = View.VISIBLE
        isExpanded = true
    }
    fun turnOffNavigationRail(){
        topAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)
        navigationRail.visibility = View.GONE
        isExpanded = false
    }
}