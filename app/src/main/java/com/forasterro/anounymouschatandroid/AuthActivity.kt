package com.forasterro.anounymouschatandroid


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.sign

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_auth)


        //Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "integración de Firebase completa")
        analytics.logEvent("InitScreen", bundle)

        //setup
        setup()

    }

    private fun setup() {
        title = "Autentication"

        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val logInButton = findViewById<Button>(R.id.logInButton)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)


        signUpButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)


                        } else {
                            showAlert()
                        }
                    }
            }
        }

        logInButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)


                        } else {
                            showAlert()
                        }
                    }
            }
        }


    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("there was an error autenticating the user")
        builder.setPositiveButton("Ok", null)
        val dialog: AlertDialog= builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent: Intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)

        }
        startActivity(homeIntent)
    }

}