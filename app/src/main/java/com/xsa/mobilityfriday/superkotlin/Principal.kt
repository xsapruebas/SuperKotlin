package com.xsa.mobilityfriday.superkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.jetbrains.anko.*


class Principal : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    lateinit var password: EditText
    lateinit var email: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            backgroundResource = R.mipmap.ic_superkotlin2
            padding = dip(30)
            email = editText {
                hint = "Name"
                textSize = 24f
            }
            password = editText {
                hint = "Password"
                textSize = 24f
                inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
            }
            button("Login") {
                textSize = 26f
                setOnClickListener { signIn() }
            }
        }

        mAuth = FirebaseAuth.getInstance()

    }

    override fun onStart() {
        super.onStart()
        currentUser = mAuth?.currentUser
    }

    fun createAccount() {
        if (validarCampos()) {
            mAuth?.createUserWithEmailAndPassword(this.email.text.toString(), this.password.text.toString())?.addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    //Registration OK
                    Toast.makeText(this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show()
                    currentUser = mAuth?.currentUser
                } else {
                    Toast.makeText(this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show()
                    //Registration error
                }
            }
        }
    }

    fun signIn() {
        if (validarCampos()) {
            mAuth?.signInWithEmailAndPassword(this.email.text.toString(), this.password.text.toString())?.addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    // Log in correcto
                    Toast.makeText(this, "Log in correcto", Toast.LENGTH_SHORT).show()
                    currentUser = mAuth?.currentUser
                    // Se pasa a la siguiente ventana
                    val intent = Intent(this, Portada::class.java)
                    intent.putExtra("EMAIL", this.email.text.toString())
                    ContextCompat.startActivity(this, intent, null)
                } else {
                    Toast.makeText(this, "Log in fallido", Toast.LENGTH_SHORT).show()
                    // Log in fallido
                }
            }
        }
    }

    // Comprobar que los campos no están vacíos antes de hacer ninguna llamada
    fun validarCampos(): Boolean {
        var valido = true

        if (this.email.text.toString().isEmpty()) {
            // Error
            Toast.makeText(this, "Email vacío", Toast.LENGTH_SHORT).show()
            valido = false
        } else {
            // Contiene algo
        }

        if (this.password.text.toString().isEmpty()) {
            // Error
            Toast.makeText(this, "Password vacío", Toast.LENGTH_SHORT).show()
            valido = false
        } else {
            // Contiene algo
        }
        return valido
    }

}
