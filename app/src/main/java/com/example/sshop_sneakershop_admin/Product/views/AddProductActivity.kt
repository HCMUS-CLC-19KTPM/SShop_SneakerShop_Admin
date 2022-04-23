package com.example.sshop_sneakershop_admin.Product.views

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.sshop_sneakershop_admin.Auth.views.SignInActivity
import com.example.sshop_sneakershop_admin.Product.controllers.ProductController
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.Review.models.Review
import com.example.sshop_sneakershop_admin.databinding.ActivityAddProductBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class AddProductActivity : AppCompatActivity(), IProductView {
    private lateinit var binding: ActivityAddProductBinding
    private lateinit var productController: ProductController
    private lateinit var categoryAdapter: ArrayAdapter<String>
    private var product: Product = Product()
    private var imageUri: Uri? = null

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
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productController = ProductController(this)
        setUpCategorySpinner()


        binding.addProductButtonAddImage.setOnClickListener {
            selectImage()
        }

        binding.addProductButtonSubmit.setOnClickListener {
            addProductBtnHandler()
        }
        binding.addProductToolbar.setNavigationOnClickListener {
            finish()
        }
    }
    fun addProductBtnHandler(){
        if(!isValidInput()){
            Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show()
            return
        }
        product.name = binding.addProductProductName.text.toString()
        product.price = binding.addProductEdittextProductPrice.text.toString().toDouble()
        product.category = binding.addProductSpinnerProductCategory.selectedItem.toString()
        product.description = binding.addProductEdittextProductDescription.text.toString()
        product.brand = binding.adminAddProductEdittextProductBrand.text.toString()
        product.discount = binding.addProductEdittextProductDiscount.text.toString().toDouble()
        product.stock = arrayListOf(binding.quantity1.text.toString().toInt(),
            binding.quantity2.text.toString().toInt(),
            binding.quantity3.text.toString().toInt(),
            binding.quantity4.text.toString().toInt())
        product.releaseDate = Date()
        product.origin = "US"
        product.rating = 0.0
        product.reviews = ArrayList<Review>()
        imageUri?.let { productController.uploadImage(it) }
    }
    fun isValidInput(): Boolean{
        if(binding.addProductProductName.text.toString().isEmpty()){
            binding.addProductProductName.error = getString(R.string.error_input_empty)
            return false
        }
        if(binding.addProductEdittextProductDescription.text.toString().isEmpty()){
            binding.addProductEdittextProductDescription.error = getString(R.string.error_input_empty)
            return false
        }
        if(binding.adminAddProductEdittextProductBrand.text.toString().isEmpty()){
            binding.adminAddProductEdittextProductBrand.error = getString(R.string.error_input_empty)
            return  false
        }
        if(binding.addProductEdittextProductPrice.text.toString().isEmpty()){
            binding.addProductEdittextProductPrice.error = getString(R.string.error_input_empty)
            return false
        }
        if(binding.addProductEdittextProductDiscount.text.toString().isEmpty()){
            binding.addProductEdittextProductDiscount.error = getString(R.string.error_input_empty)
            return false
        }
        if(imageUri == null){
            MaterialAlertDialogBuilder(this)
                .setTitle("Image Error")
                .setMessage("Please select an image")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
            return false
        }
        return true
    }
    fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            binding.addProductImageView.setImageURI(imageUri)
        }
    }
    fun setUpCategorySpinner() {
        categoryAdapter =
            ArrayAdapter(this, R.layout.spinner_item, resources.getStringArray(R.array.category_2))
        binding.addProductSpinnerProductCategory.adapter = categoryAdapter
        binding.addProductSpinnerProductCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    product.category = parent!!.getItemAtPosition(position).toString()
                }
            }
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
        MaterialAlertDialogBuilder(this)
            .setTitle("Success")
            .setMessage("Product added successfully!")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    override fun onUploadImageSuccess(url: String) {
        product.image = url
        Log.i("image-url","2: ${product.image}")
        productController.addProduct(product)
    }

}