package com.example.sshop_sneakershop_admin.Product.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.sshop_sneakershop_admin.Auth.views.SignInActivity
import com.example.sshop_sneakershop_admin.Product.controllers.ProductController
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityProductDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class ProductDetailActivity : AppCompatActivity(), IProductView {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var productController: ProductController
    private var currentProduct = Product()
    private lateinit var categoryAdapter: ArrayAdapter<String>

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

        setUpCategorySpinner()



        binding.productDetailImageviewEditPencil.setOnClickListener {
            turnOnEditMode()
        }
        binding.productDetailButtonSubmit.isEnabled = false
        binding.productDetailButtonSubmit.setOnClickListener {
            // update on name, price, discount, category, description, quantity
            turnOffEditMode()
            if(productController.updateProduct(currentProduct)){
                Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
            }
        }
        binding.itemDetailToolbar.setNavigationOnClickListener {
            finish()
        }
    }
    fun setUpCategorySpinner() {
        categoryAdapter = ArrayAdapter(this, R.layout.spinner_item, resources.getStringArray(R.array.category_2))
        binding.spinnerCategory.adapter = categoryAdapter
        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentProduct.category = parent!!.getItemAtPosition(position).toString()
            }
        }
        binding.spinnerCategory.isEnabled = false
    }
    fun turnOnEditMode() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Edit Product")
            .setMessage("Are you sure you want to open edit mode?")
            .setPositiveButton("Yes") { dialog, which ->
                binding.productDetailEdittextProductName.isFocusableInTouchMode = true
                binding.productDetailEdittextProductOldPrice.isFocusableInTouchMode = true
                binding.productDetailEdittextDiscount.isFocusableInTouchMode = true
                binding.productDetailEdittextDescriptionContent.isFocusableInTouchMode = true
                binding.productDetailQuantity1.isFocusableInTouchMode = true
                binding.productDetailQuantity2.isFocusableInTouchMode = true
                binding.productDetailQuantity3.isFocusableInTouchMode = true
                binding.productDetailQuantity4.isFocusableInTouchMode = true
                binding.spinnerCategory.isEnabled = true


                binding.productDetailEdittextProductName.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
                binding.productDetailEdittextProductOldPrice.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
                binding.productDetailEdittextDiscount.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
                binding.productDetailEdittextDescriptionContent.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
                binding.productDetailQuantity1.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
                binding.productDetailQuantity2.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
                binding.productDetailQuantity3.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
                binding.productDetailQuantity4.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
                binding.productDetailButtonSubmit.isEnabled = true
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    fun turnOffEditMode() {
        // update info to current product
        currentProduct.name = binding.productDetailEdittextProductName.text.toString()
        currentProduct.price = binding.productDetailEdittextProductOldPrice.text.toString().toDouble()
        currentProduct.discount = binding.productDetailEdittextDiscount.text.toString().toDouble()
        currentProduct.description = binding.productDetailEdittextDescriptionContent.text.toString()
        currentProduct.stock = arrayListOf(binding.productDetailQuantity1.text.toString().toInt(),
            binding.productDetailQuantity2.text.toString().toInt(),
            binding.productDetailQuantity3.text.toString().toInt(),
            binding.productDetailQuantity4.text.toString().toInt())
        currentProduct.category = binding.spinnerCategory.selectedItem.toString()

        binding.productDetailEdittextProductName.clearFocus()
        binding.productDetailEdittextProductOldPrice.clearFocus()
        binding.productDetailEdittextDiscount.clearFocus()
        binding.productDetailEdittextDescriptionContent.clearFocus()
        binding.productDetailQuantity1.clearFocus()
        binding.productDetailQuantity2.clearFocus()
        binding.productDetailQuantity3.clearFocus()
        binding.productDetailQuantity4.clearFocus()

        binding.productDetailEdittextProductName.isFocusableInTouchMode = false
        binding.productDetailEdittextProductOldPrice.isFocusableInTouchMode = false
        binding.productDetailEdittextDiscount.isFocusableInTouchMode = false
        binding.productDetailEdittextDescriptionContent.isFocusableInTouchMode = false
        binding.productDetailQuantity1.isFocusableInTouchMode = false
        binding.productDetailQuantity2.isFocusableInTouchMode = false
        binding.productDetailQuantity3.isFocusableInTouchMode = false
        binding.productDetailQuantity4.isFocusableInTouchMode = false

        binding.productDetailEdittextProductName.background = null
        binding.productDetailEdittextProductOldPrice.background = null
        binding.productDetailEdittextDiscount.background = null
        binding.productDetailEdittextDescriptionContent.background = null
        binding.productDetailQuantity1.background = null
        binding.productDetailQuantity2.background = null
        binding.productDetailQuantity3.background = null
        binding.productDetailQuantity4.background = null
        binding.spinnerCategory.isEnabled = false
        binding.productDetailButtonSubmit.isEnabled = false
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
        binding.productDetailEdittextProductOldPrice.setText(currentProduct.price.toString())
        val currentPrice =
            currentProduct.price - (currentProduct.price * currentProduct.discount / 100)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        binding.productDetailPrice.text = "$${df.format(currentPrice)}"

        binding.productDetailEdittextDiscount.setText(currentProduct.discount.toString())

        binding.productDetailEdittextDescriptionContent.setText(currentProduct.description)
        val stock = mutableListOf(0, 0, 0, 0)
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
        // category set up
        val categoryList = resources.getStringArray(R.array.category_2)
        binding.spinnerCategory.setSelection(categoryList.indexOf(currentProduct.category))
    }

    override fun onShowProductsByCategory(products: ArrayList<Product>) {
        TODO("Not yet implemented")
    }

    override fun onShowError(error: String) {
        TODO("Not yet implemented")
    }
}