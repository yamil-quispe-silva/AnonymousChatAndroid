package com.forasterro.anounymouschatandroid

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        val bundle=intent.extras
        val email=bundle?.getString("email")
        val provider=bundle?.getString("provider")

        //setup
        setup(email?: "",provider?:"")
    }

    private fun setup(email:String,provider:String){
        title = "AnounymousChat"

        val emailTextView=findViewById<TextView>(R.id.emailTextView)
        val providerTextView=findViewById<TextView>(R.id.providerTextView)
        val exitButton = findViewById<Button>(R.id.exitButton)

        emailTextView.text=email
        providerTextView.text=provider

        exitButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

    }




}