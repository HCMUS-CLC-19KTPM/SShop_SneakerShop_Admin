package com.example.sshop_sneakershop_admin.Home.views


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop_admin.Account.views.AccountListActivity
import com.example.sshop_sneakershop_admin.Auth.views.SignInActivity
import com.example.sshop_sneakershop_admin.Home.controllers.SoldProductController
import com.example.sshop_sneakershop_admin.Home.models.Chart
import com.example.sshop_sneakershop_admin.Order.views.OrderListActivity
import com.example.sshop_sneakershop_admin.Product.controllers.ProductController
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.Product.views.ItemClickListener
import com.example.sshop_sneakershop_admin.Product.views.ProductAdapter
import com.example.sshop_sneakershop_admin.Product.views.ProductDetailActivity
import com.example.sshop_sneakershop_admin.Product.views.ProductListAcitivity
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityMainBinding
import com.example.sshop_sneakershop_admin.databinding.ActivityStatisticBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), IHomeView, ItemClickListener {

    private lateinit var bindings: ActivityMainBinding
    private val auth = Firebase.auth
    private var bestSeller = ArrayList<Product>()
    private lateinit var statisticBinding: ActivityStatisticBinding

    private lateinit var productController: ProductController
    private lateinit var soldProductController: SoldProductController

    //    private lateinit var graph: GraphView
    private var chartSeries: LineGraphSeries<DataPoint> = LineGraphSeries()

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
        productController = ProductController(iHomeView = this)
        soldProductController = SoldProductController(this)

        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        statisticBinding = bindings.contentStatistic

        /// top 10 product
        val statisticActivity = bindings.contentStatistic
        statisticBinding.statisticRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = ProductAdapter(bestSeller, this@MainActivity, bestSeller, false)
        }
        productController.onGetTop10Products()

        //// end top 10 product

        val toolbar = bindings.mainToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home"


        val drawerLayout = bindings.drawerLayout
        val navigationView = bindings.navigationView

        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
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

        soldProductController.onGetStatistics()
        soldProductController.onGetBestSeller()
    }

    override fun onGetStatisticsSuccess(soldProduct: ArrayList<Chart>) {
        // graph config
//        graph = statisticBinding.graph
        for(item in soldProduct){
            val zonedDateTime: ZonedDateTime = item.createdAt.atStartOfDay(ZoneId.systemDefault())
            val x = Date.from(zonedDateTime.toInstant())
            val y = item.total
            chartSeries.appendData(DataPoint(x, y), true, 90)
        }
        // set manual X bounds
        statisticBinding.graph.addSeries(chartSeries)
        chartSeries.title = "Income"
        chartSeries.thickness = 8
        chartSeries.isDrawDataPoints = true
        statisticBinding.graph.legendRenderer.isVisible = true
        statisticBinding.graph.legendRenderer.align = LegendRenderer.LegendAlign.TOP
        val gridLabel: GridLabelRenderer = statisticBinding.graph.gridLabelRenderer
        gridLabel.horizontalAxisTitle = "Day In Week"
        gridLabel.horizontalAxisTitleTextSize = 50F
        // axis titles
        val staticLabelsFormatter = StaticLabelsFormatter(statisticBinding.graph)
        staticLabelsFormatter.setHorizontalLabels(soldProduct.map { it.createdAt.toString() }.toTypedArray())

//        staticLabelsFormatter.setHorizontalLabels(arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"))
//        staticLabelsFormatter.setVerticalLabels(arrayOf("low", "middle", "high"))
        statisticBinding.graph.gridLabelRenderer.labelFormatter = staticLabelsFormatter

        /// end graph config
    }

    override fun onGetStatisticsFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetBestSellerSuccess(bestSeller: ArrayList<Product>) {
        this.bestSeller.clear()
        this.bestSeller.addAll(bestSeller)
        bindings.contentStatistic.statisticRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onGetBestSellerFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetailActivity::class.java)
        intent.putExtra("item-id", product.id)
        startActivity(intent)
    }
}