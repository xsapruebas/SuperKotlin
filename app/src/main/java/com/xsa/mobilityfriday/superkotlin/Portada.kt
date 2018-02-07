package com.xsa.mobilityfriday.superkotlin

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_portada.*
import kotlinx.android.synthetic.main.item_superhero_list.view.*

class Portada : AppCompatActivity() {

    lateinit var mRecyclerView: RecyclerView
    val mAdapter: RecyclerAdapter = RecyclerAdapter()

    var dbSuperHero = FirebaseDatabase.getInstance()
    var myRef = dbSuperHero.reference
    var superheros: MutableList<SuperHero> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portada)
        AsyncTaskExample().execute()
    }

    fun setUpRecyclerView(lista: MutableList<SuperHero>) {
        mRecyclerView = rvSuperheroList
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter.RecyclerAdapter(lista, this)
        mRecyclerView.adapter = mAdapter
    }

    fun cargarSuperheros(): MutableList<SuperHero> {
        //val superheros: MutableList<SuperHero> = ArrayList()
        myRef.child("SuperHero").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot?) {
                val it = snapshot!!.children.iterator()
                while (it.hasNext()) {
                    var info = it.next()
                    Log.i("INFO", "Nombre: " + info.child("Nombre").value)
                    Log.i("INFO", "Nombre Real: " + info.child("NombreReal").value)
                    Log.i("INFO", "Publisher: " + info.child("Publisher").value)
                    Log.i("INFO", "Descripcion: " + info.child("Descripcion").value)
                    var nombre = info.child("Nombre").value
                    var nombreReal = info.child("NombreReal").value
                    var publisher = info.child("Publisher").value
                    var descripcion = info.child("Descripcion").value
                    var avatar = info.child("Avatar").value
                    superheros.add(SuperHero(nombre.toString(), nombreReal.toString(), publisher.toString(), descripcion.toString(), avatar.toString()))
                }
                setUpRecyclerView(superheros)
            }

            override fun onCancelled(snapshot: DatabaseError?) {
                Log.e("ERROR", "Algo no ha funcionado bien")
            }
        })
        return superheros
    }

    inner class AsyncTaskExample : AsyncTask<String, MutableList<SuperHero>, MutableList<SuperHero>>() {

        override fun onPreExecute() {
            super.onPreExecute()

        }

        override fun doInBackground(vararg p0: String?): MutableList<SuperHero> {

            var result: MutableList<SuperHero> = ArrayList<SuperHero>()

            try {
                result = cargarSuperheros()
            } catch (Ex: Exception) {
                Log.d("", "Error in doInBackground " + Ex.message)
            }
            return result
        }

        override fun onPostExecute(result: MutableList<SuperHero>) {
            super.onPostExecute(result)

            if (result != null) {
                setUpRecyclerView(result)
            }

        }
    }

}

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var superheros: MutableList<SuperHero> = ArrayList()
    lateinit var context: Context

    fun RecyclerAdapter(superheros: MutableList<SuperHero>, context: Context) {
        this.superheros = superheros
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = superheros.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_superhero_list, parent, false))
    }

    override fun getItemCount(): Int {
        return superheros.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val superheroName = view.tvSuperhero
        val realName = view.tvRealName
        val publisher = view.tvPublisher
        val avatar = view.ivAvatar

        fun bind(superhero: SuperHero, context: Context) {
            superheroName.setText(superhero.nombre)
            realName.setText(superhero.nombreReal)
            publisher.setText(superhero.publisher)
            avatar.loadUrl(superhero.avatar)

            itemView.setOnClickListener { abrirTarjeta(context, superhero) }
        }

        fun ImageView.loadUrl(url: String) {
            Picasso.with(context).load(url).into(this)
        }

        fun abrirTarjeta(context: Context, superhero: SuperHero) {
            val intent = Intent(context, Personaje::class.java)
            intent.putExtra("DESCRIPCION", superhero.descripcion)
            intent.putExtra("AVATAR", superhero.avatar)
            ContextCompat.startActivity(context, intent, null)
            Toast.makeText(context, "DESCRIPCION DE: " + superhero.nombre, Toast.LENGTH_SHORT).show()
        }
    }
}