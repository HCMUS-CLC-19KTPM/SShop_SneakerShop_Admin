package com.example.sshop_sneakershop_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.sshop_sneakershop_admin.Account.Views.AccountListActivity
import com.example.sshop_sneakershop_admin.Product.ProductListAcitivity
import com.example.sshop_sneakershop_admin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var toolbar = binding.mainToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home"


        var drawerLayout = binding.drawerLayout
        var navigationView = binding.navigationView

        var actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_product -> {
                    val intent = Intent(this, ProductListAcitivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_account -> {
                    val intent = Intent(this, AccountListActivity::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawers()
            true
        }

    }
}