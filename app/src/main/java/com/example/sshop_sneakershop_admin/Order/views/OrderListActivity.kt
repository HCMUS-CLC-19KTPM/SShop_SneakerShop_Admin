package com.example.sshop_sneakershop_admin.Order.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Auth.views.SignInActivity
import com.example.sshop_sneakershop_admin.Order.controllers.OrderController
import com.example.sshop_sneakershop_admin.Order.models.Order
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityOrderListBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OrderListActivity : AppCompatActivity(),IOrderView, OrderClickListener {
    private var orders: ArrayList<Order> = ArrayList()
    private lateinit var binding: ActivityOrderListBinding
    private lateinit var orderController: OrderController
    private lateinit var orderAdapter: OrderAdapter
    private var fullOrderList: ArrayList<Order> = ArrayList()

    override fun onStart() {
        super.onStart()
        val auth = Firebase.auth
        if (auth.currentUser == null || !auth.currentUser!!.isEmailVerified) {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderController = OrderController(this)
        binding = ActivityOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val orderActivity = this
        binding.orderListRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        orderAdapter = OrderAdapter(orders, orderActivity, fullOrderList)
        binding.orderListRecyclerView.adapter = orderAdapter

        orderController.onGetAllOrders()
        setSupportActionBar(binding.orderListToolbar)
        binding.orderListToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_with_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                orderAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onShowAllOrder(orders: ArrayList<Order>) {
        this.orders.clear()
        this.fullOrderList.clear()
        this.orders.addAll(orders)
        this.fullOrderList.addAll(orders)
        binding.orderListRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onShowOrderDetail(order: Order) {
        TODO("Not yet implemented")
    }

    override fun onUpdateOrderSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateOrderFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onClick(order: Order) {
        val intent = Intent(applicationContext, OrderDetailActivity::class.java)
        intent.putExtra("item-id", order.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        orderController.onGetAllOrders()
        binding.orderListToolbar.collapseActionView()
    }
}