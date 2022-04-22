package com.example.sshop_sneakershop_admin.Order.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop_admin.Auth.views.SignInActivity
import com.example.sshop_sneakershop_admin.Order.controllers.OrderController
import com.example.sshop_sneakershop_admin.Order.models.Order
import com.example.sshop_sneakershop_admin.Product.controllers.ProductController
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.Product.views.IProductView
import com.example.sshop_sneakershop_admin.Product.views.ItemClickListener
import com.example.sshop_sneakershop_admin.Product.views.ProductAdapter
import com.example.sshop_sneakershop_admin.Product.views.ProductDetailActivity
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityOrderDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class OrderDetailActivity : AppCompatActivity(), IOrderView, ItemClickListener, IProductView {
    private lateinit var binding: ActivityOrderDetailBinding
    private lateinit var orderController: OrderController
    private lateinit var statusAdapter: ArrayAdapter<String>
    private var order: Order = Order()
    private var products: ArrayList<Product> = ArrayList()
    private lateinit var productController: ProductController
    private var status = ""

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
        productController = ProductController(this)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val orderID = intent.getStringExtra("item-id").toString()
        orderController.onGetOrderById(orderID)
        setUpStatusSpinner()

        val orderDetailActivity = this
        binding.orderDetailRecyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = ProductAdapter(products, orderDetailActivity, products, true)
        }
        binding.orderDetailButtonConfirm.setOnClickListener {
            Log.i("update_order", "${order.id + " " + status}")
            MaterialAlertDialogBuilder(this)
                .setTitle("Confirm")
                .setMessage("Are you sure to update this order?")
                .setPositiveButton("Yes") { dialog, which ->
                    orderController.updateOrder(order.id, status)
                }
                .setNegativeButton("No") { dialog, which ->
                    val orderStatusList = resources.getStringArray(R.array.order_status)
                    binding.orderDetailSpinner.setSelection(orderStatusList.indexOf(order.deliveryStatus))
                    dialog.dismiss()
                }
                .show()
        }
        binding.orderDetailToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    fun setUpStatusSpinner(){
        statusAdapter = ArrayAdapter(this, R.layout.spinner_item, resources.getStringArray(R.array.order_status))
        binding.orderDetailSpinner.adapter = statusAdapter
        binding.orderDetailSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                status = statusAdapter.getItem(position).toString()
            }
        }
    }
    override fun onShowAllOrder(orders: ArrayList<Order>) {
        TODO("Not yet implemented")
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n", "NotifyDataSetChanged")
    override fun onShowOrderDetail(order: Order) {
        this.order = Order(order)
        binding.orderDetailTextviewDeliveryDescription.text = "Standard Express - " + this.order.id
        val formatter = SimpleDateFormat("dd - MM - yyyy")
        val startDate = formatter.format(this.order.startDate)
        binding.orderDetailTextviewStartDate.text = startDate
        val endDate = formatter.format(this.order.endDate)
        binding.orderDetailTextviewEstimatedDate2.text = endDate
        binding.orderDetailTextviewCustomerName.text = this.order.name
        binding.orderDetailTextviewCustomerPhone.text = this.order.phone
        binding.orderDetailTextviewCustomerAddress.text = this.order.address
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        binding.orderDetailTextviewTotalCost.text = "$${df.format(this.order.totalCost)}"
        binding.orderDetailTextviewStatus.text = this.order.deliveryStatus
        val orderStatusList = resources.getStringArray(R.array.order_status)
        binding.orderDetailSpinner.setSelection(orderStatusList.indexOf(this.order.deliveryStatus))
        products.clear()
        products.addAll(this.order.cart)
        binding.orderDetailRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onUpdateOrderSuccess(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Success")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
//                SystemClock.sleep(1000)
                this.recreate()
            }
            .show()
    }

    override fun onUpdateOrderFailed(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Failed")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetailActivity::class.java)
        intent.putExtra("item-id", product.id)
        startActivity(intent)
    }


    override fun onShowAllProducts(products: ArrayList<Product>) {
        TODO("Not yet implemented")
    }

    override fun onShowProductDetail(product: Product) {
        TODO("Not yet implemented")
    }

    override fun onShowProductsByCategory(products: ArrayList<Product>) {
        TODO("Not yet implemented")
    }

    override fun onShowMessage(msg: String) {
        TODO("Not yet implemented")
    }

    override fun onAddProductSuccess(product: Product) {
        TODO("Not yet implemented")
    }

    override fun onUploadImageSuccess(url: String) {
        TODO("Not yet implemented")
    }
}