package com.example.sshop_sneakershop_admin.Home.views


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop_admin.Account.views.AccountListActivity
import com.example.sshop_sneakershop_admin.Auth.views.SignInActivity
import com.example.sshop_sneakershop_admin.Order.OrderListActivity
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.Product.views.ProductListAcitivity
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityMainBinding
import com.example.sshop_sneakershop_admin.databinding.ActivityStatisticBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMainBinding
    private val auth = Firebase.auth

    private lateinit var lineList: ArrayList<Entry>
    private lateinit var lineDataSet: LineDataSet
    private lateinit var lineData: LineData
    private lateinit var products: ArrayList<Product>



    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        val statisticBinding = bindings.contentStatistic

        var toolbar = bindings.mainToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home"


        var drawerLayout = bindings.drawerLayout
        var navigationView = bindings.navigationView

        var actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.openNavDrawer,
            R.string.closeNavDrawer
        )
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
                R.id.nav_order -> {
                    val intent = Intent(this, OrderListActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_account -> {
                    val intent = Intent(this, AccountListActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_signout ->{
                    auth.signOut()

                    // only use for demo
//                    val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestIdToken(application.getString(R.string.default_web_client_id))
//                        .requestEmail()
//                        .build()
//
//                    val googleClient = GoogleSignIn.getClient(application, options)
//                    googleClient.signOut()

                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawers()
            true
        }
        lineChartSetting(statisticBinding)
        top10ProductSetting(statisticBinding)
    }
    fun lineChartSetting(binding: ActivityStatisticBinding){
        lineList = ArrayList()
        lineList.add(Entry(1f, 200f))
        lineList.add(Entry(2f, 300f))
        lineList.add(Entry(3f, 400f))
        lineList.add(Entry(4f, 500f))
        lineList.add(Entry(5f, 600f))
        lineList.add(Entry(6f, 700f))

        lineDataSet = LineDataSet(lineList, "Line Chart")
        lineData = LineData(lineDataSet)
        binding.statisticLineChart!!.data = lineData
        lineDataSet.color = Color.BLACK
        lineDataSet.valueTextColor = Color.BLUE
        lineDataSet.valueTextSize=20f
        lineDataSet.setDrawFilled(true)
    }
    fun top10ProductSetting(binding: ActivityStatisticBinding){


    }
}