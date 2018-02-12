package com.xsa.mobilityfriday.superkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.jetbrains.anko.*

/**
 * Created by xsantibanez on 08/02/2018.
 */
class CrearSuperHero : AppCompatActivity() {

    lateinit var tvNombre: TextView
    lateinit var tvNombreReal: TextView
    lateinit var tvPublisher: TextView
    lateinit var tvDescripcion: TextView
    lateinit var etNombre: EditText
    lateinit var etNombreReal: EditText
    lateinit var etPublisher: EditText
    lateinit var etDescripcion: EditText
    lateinit var btAgregar: Button

    // Iniciar los IDs para poder localizarlos
    val ID_TVNOMBRE = 1
    val ID_ETNOMBRE = 2
    val ID_TVNOMBREREAL = 3
    val ID_ETNOMBREREAL = 4
    val ID_TVPUBLISHER = 5
    val ID_ETPUBLISHER = 6
    val ID_TVDESCRIPCION = 7
    val ID_ETDESCRIPCION = 8
    val ID_BTAGREGAR = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        relativeLayout {
            //Nombre
            tvNombre = textView {
                id = ID_TVNOMBRE
                hint = "NOMBRE"
                leftPadding = 100
                topPadding = 50
            }
            etNombre = editText {
                id = ID_ETNOMBRE
                hint = "Introduce el nombre"
                width = 650
            }.lparams {
                leftMargin = 150
                rightOf(tvNombre.id)
                baselineOf(tvNombre.id)
            }
            // Nombre Real
            tvNombreReal = textView {
                id = ID_TVNOMBREREAL
                hint = "NOMBRE REAL"
                leftPadding = 100
                topPadding = 50
            }.lparams {
                below(tvNombre.id)
            }
            etNombreReal = editText {
                id = ID_ETNOMBREREAL
                hint = "Introduce el nombre real"
                width = 650
            }.lparams {
                below(etNombre.id)
                rightOf(tvNombreReal.id)
                baselineOf(tvNombreReal.id)
                alignStart(etNombre.id)
            }
            // Publisher
            tvPublisher = textView {
                id = ID_TVPUBLISHER
                hint = "PUBLISHER"
                leftPadding = 100
                topPadding = 50
            }.lparams {
                below(tvNombreReal.id)
            }
            etPublisher = editText {
                id = ID_ETPUBLISHER
                hint = "Introduce la publicacion"
                width = 650
            }.lparams {
                below(etNombreReal.id)
                rightOf(tvPublisher.id)
                baselineOf(tvPublisher.id)
                alignStart(etNombre.id)
            }
            //Descripcion
            tvDescripcion = textView {
                id = ID_TVDESCRIPCION
                hint = "DESCRIPCION"
                leftPadding = 100
                topPadding = 50
            }.lparams {
                below(tvPublisher.id)
            }
            etDescripcion = editText {
                id = ID_ETDESCRIPCION
                hint = "Introduce la descripcion"
                width = 650
                minLines = 6
            }.lparams {
                below(etPublisher.id)
                rightOf(tvDescripcion.id)
                baselineOf(tvDescripcion.id)
                alignStart(etNombre.id)
            }
            // Boton Agregar
            btAgregar = button {
                id = ID_BTAGREGAR
                hint = "Agregar"
                width = 500
                setOnClickListener {
                    Log.i("INFO", "Se está pulsando el botón agregar")
                }
            }.lparams {
                below(etDescripcion.id)
                centerHorizontally()
            }
        }
    }
}