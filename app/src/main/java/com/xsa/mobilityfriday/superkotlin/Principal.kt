package com.xsa.mobilityfriday.superkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_principal.*


class Principal : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        mAuth = FirebaseAuth.getInstance()

        email_sign_in_button.setOnClickListener { signIn(field_email.text.toString(), field_password.text.toString()) }
        email_create_account_button.setOnClickListener { createAccount(field_email.text.toString(), field_password.text.toString()) }

    }

    override fun onStart() {
        super.onStart()
        currentUser = mAuth?.currentUser
    }

    fun createAccount(email: String, password: String) {
        if (validarCampos()) {
            mAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener { task: Task<AuthResult> ->
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

    fun signIn(email: String, password: String) {
        if (validarCampos()) {
            mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    // Log in correcto
                    Toast.makeText(this, "Log in correcto", Toast.LENGTH_SHORT).show()
                    currentUser = mAuth?.currentUser
                    // Se pasa a la siguiente ventana
                    val intent = Intent(this, Portada::class.java)
                    intent.putExtra("EMAIL", email)
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

        //CAMBIAR POR EL CAMPO DEL EMAIL
        val email = field_email.text.toString()
        //CAMBIAR POR EL CAMPO DEL PASSWORD
        val password = field_password.text.toString()

        if (email.isEmpty()) {
            // Error
            Toast.makeText(this, "Email vacío", Toast.LENGTH_SHORT).show()
            valido = false
        } else {
            // Contiene algo
        }

        if (password.isEmpty()) {
            // Error
            Toast.makeText(this, "Password vacío", Toast.LENGTH_SHORT).show()
            valido = false
        } else {
            // Contiene algo
        }
        return valido
    }

}
