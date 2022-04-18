package com.example.sshop_sneakershop_admin.Product.Views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.sshop_sneakershop_admin.Auth.views.SignInActivity
import com.example.sshop_sneakershop_admin.Product.controllers.ProductController
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityProductDetailBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class ProductDetailActivity : AppCompatActivity(), IProductView {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var productController: ProductController
    private var currentProduct = Product()

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
        productController = ProductController(this)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val productID = intent.getStringExtra("item-id").toString()
        Log.i("product-id", "$productID")
        productController.onGetProductById(productID)

        binding.productDetailImageviewEditPencil.setOnClickListener {
            turnOnEditMode()
        }
        binding.productDetailButtonSubmit.setOnClickListener {
            turnOfEditMode()
        }
        binding.itemDetailToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    fun turnOnEditMode() {
//        productNameET!!.isFocusableInTouchMode = true
//        productPriceET!!.isFocusableInTouchMode = true
//        productDescET!!.isFocusableInTouchMode = true
//        productInfoET!!.isFocusableInTouchMode = true
//
//        productNameET!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
//        productPriceET!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
//        productDescET!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
//        productInfoET!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
//        submitBtn!!.isEnabled = true
    }

    fun turnOfEditMode() {
//        productNameET!!.clearFocus()
//        productPriceET!!.clearFocus()
//        productDescET!!.clearFocus()
//        productInfoET!!.clearFocus()
//
//        productNameET!!.isFocusableInTouchMode = false
//        productPriceET!!.isFocusableInTouchMode = false
//        productDescET!!.isFocusableInTouchMode = false
//        productInfoET!!.isFocusableInTouchMode = false
//
//        productNameET!!.background = null
//        productPriceET!!.background = null
//        productDescET!!.background = null
//        productInfoET!!.background = null
//
//        submitBtn!!.isEnabled = false
    }

    override fun onShowAllProducts(products: ArrayList<Product>) {
        TODO("Not yet implemented")
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onShowProductDetail(product: Product) {
        this.currentProduct = Product(product)
        binding.productDetailTextviewCategoryTag.text = currentProduct.category
        if (TextUtils.isEmpty(currentProduct.image)) {
            binding.productDetailImageview.setImageResource(R.drawable.shoe)
        } else {
            Picasso.get().load(currentProduct.image).into(binding.productDetailImageview)
        }
        binding.productDetailEdittextProductName.setText(currentProduct.name)
        val ratingValue = currentProduct.rating
        binding.productDetailTextviewRating.text = "$ratingValue/5.00"
        binding.productDetailEdittextProductOldPrice.setText("$${currentProduct.price}")
        val currentPrice =
            currentProduct.price - (currentProduct.price * currentProduct.discount / 100)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        binding.productDetailPrice.text = "$${df.format(currentPrice)}"

        binding.productDetailEdittextDiscount.setText("${currentProduct.discount}%")

        binding.productDetailEdittextDescriptionContent.setText(currentProduct.description)
        var stock = mutableListOf(0, 0, 0, 0)
        for (i in 0..currentProduct.stock.size - 1) {
            stock[i] = currentProduct.stock[i]
        }
        Log.i("stock", stock.toString())
        binding.productDetailQuantity1.setText(stock[0].toString())
        binding.productDetailQuantity2.setText(stock[1].toString())
        binding.productDetailQuantity3.setText(stock[2].toString())
        binding.productDetailQuantity4.setText(stock[3].toString())
        // more information
        val formatter = SimpleDateFormat("dd - MM - yyyy")
        val releaseDate = formatter.format(product.releaseDate)
        val moreInfo =
            "Origin: ${currentProduct.origin}\nBrand: ${currentProduct.brand}\nStyle: ${currentProduct.category}\nReleased date: $releaseDate"

        binding.productDetailEdittextInfoContent.text = moreInfo
    }

    override fun onShowProductsByCategory(products: ArrayList<Product>) {
        TODO("Not yet implemented")
    }

    override fun onShowError(error: String) {
        TODO("Not yet implemented")
    }
}