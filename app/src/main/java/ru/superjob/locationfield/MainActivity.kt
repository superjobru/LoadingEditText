package ru.superjob.locationfield

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        locationEditText.setActionClickListener {
            Toast.makeText(applicationContext, "In progress", Toast.LENGTH_SHORT).show()
        }

        locationEditText.setCustomLoadingDrawable(R.drawable.avd_anim)

        locationEditText.setActionClickListener {
            //handle action
        }

        button_reset.setOnClickListener {
            locationEditText.setLoading(false)
        }

        button_reset2.setOnClickListener {
            locationEditText2.setLoading(false)
        }
    }
}