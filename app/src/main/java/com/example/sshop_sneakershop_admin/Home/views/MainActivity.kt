package com.example.sshop_sneakershop_admin.Home.views


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop_admin.Account.views.AccountListActivity
import com.example.sshop_sneakershop_admin.Auth.views.SignInActivity
import com.example.sshop_sneakershop_admin.Order.views.OrderListActivity
import com.example.sshop_sneakershop_admin.Product.controllers.ProductController
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.Product.views.ItemClickListener
import com.example.sshop_sneakershop_admin.Product.views.ProductAdapter
import com.example.sshop_sneakershop_admin.Product.views.ProductDetailActivity
import com.example.sshop_sneakershop_admin.Product.views.ProductListAcitivity
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class MainActivity : AppCompatActivity(), IHomeView, ItemClickListener {

    private lateinit var bindings: ActivityMainBinding
    private val auth = Firebase.auth
    private lateinit var productController: ProductController
    private var top10Products = ArrayList<Product>()

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

        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        productController = ProductController(null, this)

        val statisticBinding = bindings.contentStatistic
        // graph config
//        graph = statisticBinding.graph
        for(x in 2..8){
            var y = Math.sin(2 * x * 0.2)
            chartSeries.appendData(DataPoint(x.toDouble(), y), true, 90)
        }
        // set manual X bounds
        statisticBinding.graph.addSeries(chartSeries)
        chartSeries.title = "Income"
        chartSeries.thickness = 8
        chartSeries.isDrawDataPoints = true
        statisticBinding.graph.legendRenderer.isVisible = true
        statisticBinding.graph.legendRenderer.align = LegendRenderer.LegendAlign.TOP
        val gridLable: GridLabelRenderer = statisticBinding.graph.gridLabelRenderer
        gridLable.horizontalAxisTitle = "Day In Week"
        gridLable.horizontalAxisTitleTextSize = 50F
        // axis titles
        val staticLabelsFormatter = StaticLabelsFormatter(statisticBinding.graph)
        staticLabelsFormatter.setHorizontalLabels(arrayOf("2", "3", "4", "5", "6", "7", "8"))

//        staticLabelsFormatter.setHorizontalLabels(arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"))
//        staticLabelsFormatter.setVerticalLabels(arrayOf("low", "middle", "high"))
        statisticBinding.graph.gridLabelRenderer.labelFormatter = staticLabelsFormatter

        /// end graph config
        /// top 10 product
        val statisticActivity = bindings.contentStatistic
        statisticBinding.statisticRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = ProductAdapter(top10Products, this@MainActivity, top10Products, false)
        }
        productController.onGetTop10Products()

        //// end top 10 product

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
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onShowTop10Products(products: ArrayList<Product>) {
        this.top10Products.clear()
        this.top10Products.addAll(products)
        Log.i("top10", top10Products.size.toString())
        bindings.contentStatistic.statisticRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetailActivity::class.java)
        intent.putExtra("item-id", product.id)
        startActivity(intent)
    }

}