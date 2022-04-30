package com.example.sshop_sneakershop_admin.Home.views


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
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

//    private lateinit var pieChart: PieChart

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
//        pieChart = findViewById(R.id.pieChart)
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

        initPieChart()
        setDataToPieChart()

    }

    override fun onGetStatisticsSuccess(soldProduct: ArrayList<Chart>) {
        // graph config
//        graph = statisticBinding.graph
//        for(item in soldProduct){
//            val zonedDateTime: ZonedDateTime = item.createdAt.atStartOfDay(ZoneId.systemDefault())
//            val x = Date.from(zonedDateTime.toInstant())
//            val y = item.total
//            chartSeries.appendData(DataPoint(x, y), true, 90)
//        }
//        // set manual X bounds
////        statisticBinding.graph.addSeries(chartSeries)
//        chartSeries.title = "Income"
//        chartSeries.thickness = 8
//        chartSeries.isDrawDataPoints = true
//        statisticBinding.graph.legendRenderer.isVisible = true
//        statisticBinding.graph.legendRenderer.align = LegendRenderer.LegendAlign.TOP
//        val gridLabel: GridLabelRenderer = statisticBinding.graph.gridLabelRenderer
//        gridLabel.horizontalAxisTitle = "Day In Week"
//        gridLabel.horizontalAxisTitleTextSize = 50F
        // axis titles
//        val staticLabelsFormatter = StaticLabelsFormatter(statisticBinding.graph)
//        staticLabelsFormatter.setHorizontalLabels(soldProduct.map { it.createdAt.toString() }.toTypedArray())

//        staticLabelsFormatter.setHorizontalLabels(arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"))
//        staticLabelsFormatter.setVerticalLabels(arrayOf("low", "middle", "high"))
//        statisticBinding.graph.gridLabelRenderer.labelFormatter = staticLabelsFormatter

        /// end graph

//        initPieChart()
//        setDataToPieChart()
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

    private fun initPieChart() {
        statisticBinding.pieChart.setUsePercentValues(true)
        statisticBinding.pieChart.description.text = ""
        //hollow pie chart
        statisticBinding.pieChart.isDrawHoleEnabled = false
        statisticBinding.pieChart.setTouchEnabled(false)
        statisticBinding.pieChart.setDrawEntryLabels(false)
        //adding padding
        statisticBinding.pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        statisticBinding.pieChart.setUsePercentValues(true)
        statisticBinding.pieChart.isRotationEnabled = false
        statisticBinding.pieChart.setDrawEntryLabels(false)
        statisticBinding.pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        statisticBinding.pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        statisticBinding.pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        statisticBinding.pieChart.legend.isWordWrapEnabled = true

    }

    private fun setDataToPieChart() {
        statisticBinding.pieChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(72f, "Android"))
        dataEntries.add(PieEntry(26f, "Ios"))
        dataEntries.add(PieEntry(2f, "Other"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#4DD0E1"))
        colors.add(Color.parseColor("#FFF176"))
        colors.add(Color.parseColor("#FF8A65"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        statisticBinding.pieChart.data = data
        data.setValueTextSize(15f)
        statisticBinding.pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        statisticBinding.pieChart.animateY(1400, Easing.EaseInOutQuad)

        //create hole in center
        statisticBinding.pieChart.holeRadius = 58f
        statisticBinding.pieChart.transparentCircleRadius = 61f
        statisticBinding.pieChart.isDrawHoleEnabled = true
        statisticBinding.pieChart.setHoleColor(Color.WHITE)

        //add text in center
        statisticBinding.pieChart.setDrawCenterText(true)
        statisticBinding.pieChart.centerText = "Mobile OS Market share"

        statisticBinding.pieChart.invalidate()
    }
}