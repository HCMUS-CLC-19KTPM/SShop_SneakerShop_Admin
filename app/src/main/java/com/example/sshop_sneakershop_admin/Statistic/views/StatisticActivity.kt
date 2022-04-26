package com.example.sshop_sneakershop_admin.Statistic

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityStatisticBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class StatisticActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticBinding
    private lateinit var graph: GraphView
    private lateinit var chartSeries: LineGraphSeries<DataPoint>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
//        binding = ActivityStatisticBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        graph = binding.graph
//        var y = 0.0
//        for(x in 0..90){
//            y = Math.sin(2*x*0.2) - 2*Math.sin(x*0.2)
//            chartSeries.appendData(DataPoint(x.toDouble(), y), true, 90)
//        }
//        graph.addSeries(chartSeries)



    }


}