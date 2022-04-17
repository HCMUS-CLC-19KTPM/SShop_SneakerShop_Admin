package com.example.sshop_sneakershop_admin.Auth.views

import android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT
import android.app.AlertDialog.THEME_HOLO_LIGHT
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.sshop_sneakershop_admin.Auth.controllers.AuthController
import com.example.sshop_sneakershop_admin.Home.views.MainActivity
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity(), IAuthView {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var controller: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_in)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = AuthController(this)

        binding.signinInputEmail.setText("sshopsneaker@gmail.com")
        binding.signinInputPassword.setText("hcmus1234")

        binding.signinTextviewForgotPassword.setOnClickListener{
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.signinButtonLogin.setOnClickListener {
            controller.onSignIn(binding.signinInputEmail.text.toString(), binding.signinInputPassword.text.toString())
        }
        googleSignIn()
    }

    /**
     * Sign in with google
     */
    private fun googleSignIn() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.signinButtonRegisterWithGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, 100)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                controller.onSignInWithGoogle(credential)
            }

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w("Google Sign In", "Google sign in failed", e)
            }
        }
    }

    override fun onLoginSuccess(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginFailed(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Login failed")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onForgotPasswordSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onForgotPasswordFailed(message: String) {
        TODO("Not yet implemented")
    }
}