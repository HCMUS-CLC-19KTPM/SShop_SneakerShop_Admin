package com.example.sshop_sneakershop_admin.Statistic

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Product.Product
import com.example.sshop_sneakershop_admin.Product.Views.ProductAdapter
import com.example.sshop_sneakershop_admin.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigationrail.NavigationRailView

class StatisticActivity : AppCompatActivity() {

    private lateinit var lineList: ArrayList<Entry>
    private lateinit var lineDataSet: LineDataSet
    private lateinit var lineData: LineData
    private lateinit var products: ArrayList<Product>
    private var line_chart: LineChart? = null
    private var productRecyclerView: RecyclerView? = null

    private lateinit var topAppBar: MaterialToolbar
    private lateinit var navigationRail: NavigationRailView
    private var isExpanded: Boolean  = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
        line_chart = findViewById(R.id.statistic_line_chart)
        productRecyclerView = findViewById(R.id.statistic_recycler_view)

        topAppBar = findViewById(R.id.statistic_list_toolbar)
        navigationRail = findViewById(R.id.statistic_navigationRail)

        navigationRail.visibility = View.GONE


        lineChartSetting()
        top10ProductSetting()

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            Toast.makeText(this, "Back pressed", Toast.LENGTH_SHORT).show()
            if (isExpanded){
                topAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_24)
                navigationRail.visibility = View.GONE
                isExpanded = false
            }
            else{
                topAppBar.setNavigationIcon(R.drawable.ic_baseline_close_24)
                navigationRail.visibility = View.VISIBLE
                isExpanded = true
            }
        }

//        navigationRail.setOnItemSelectedListener { item ->
//            when(item.itemId) {
//                R.id.home -> {
//                    // Respond to navigation item 1 reselection
//                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.manage_accounts -> {
//                    // Respond to navigation item 2 reselection
//                    Intent(this, AccountListActivity::class.java).also {
//                        startActivity(it)
//                    }
//                    true
//                }
//                R.id.manage_products->{
//                    Intent(this, ProductListAcitivity::class.java).also {
//                        startActivity(it)
//                    }
//                    true
//                }
//                else -> false
//            }
//        }


    }
    fun lineChartSetting(){
        lineList = ArrayList()
        lineList.add(Entry(1f, 200f))
        lineList.add(Entry(2f, 300f))
        lineList.add(Entry(3f, 400f))
        lineList.add(Entry(4f, 500f))
        lineList.add(Entry(5f, 600f))
        lineList.add(Entry(6f, 700f))

        lineDataSet = LineDataSet(lineList, "Line Chart")
        lineData = LineData(lineDataSet)
        line_chart!!.data = lineData
        lineDataSet.color = Color.BLACK
        lineDataSet.valueTextColor = Color.BLUE
        lineDataSet.valueTextSize=20f
        lineDataSet.setDrawFilled(true)
    }
    fun top10ProductSetting(){
        val product = Product("123", 1000.0,"Nike Jordan", "jordan.jpg", "Long description", 10);
        products = listOf(
            product,
            product,
            product,
            product,
            product,
            product,
            product,
            product
        ).toCollection(ArrayList())

        val adapter = ProductAdapter(products)

        productRecyclerView!!.adapter = adapter
        productRecyclerView!!.layoutManager = LinearLayoutManager(this) //GridLa
    }

}