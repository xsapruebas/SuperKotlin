package com.xsa.mobilityfriday.superkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_personaje.*

class Personaje : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje)

        ivAvatar.loadUrl(intent.getStringExtra("AVATAR"))
        tvDescripcion.text = intent.getStringExtra("DESCRIPCION")
    }

    fun ImageView.loadUrl(url: String) {
        Picasso.with(context).load(url).into(this)
    }
}