package com.example.sshop_sneakershop_admin.Statistic

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Product.Product
import com.example.sshop_sneakershop_admin.Product.Views.ProductAdapter
import com.example.sshop_sneakershop_admin.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate

class StatisticActivity : AppCompatActivity() {

    lateinit var lineList: ArrayList<Entry>
    lateinit var lineDataSet: LineDataSet
    lateinit var lineData: LineData
    lateinit var products: ArrayList<Product>
    var line_chart: LineChart? = null
    var productRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
        line_chart = findViewById(R.id.statistic_line_chart)
        productRecyclerView = findViewById(R.id.statistic_recycler_view)


        lineChartSetting()
        top10ProductSetting()
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