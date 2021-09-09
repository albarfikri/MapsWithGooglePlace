package com.albar.nearestplace

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.albar.nearestplace.databinding.ActivityMainBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var mGoogleMap: GoogleMap? = null
    var mLatitude = 0.0
    var mLongitude = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["apiKey"]
        val apiKey = value.toString()

        val fragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        fragment!!.getMapAsync { googleMap ->
            mGoogleMap = googleMap
            initMap()
        }

        val myAdapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_list_item_1, resources.getStringArray(R.array.cari_tempat)
        )

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnCari.adapter = myAdapter
        // Listener
        binding.spnCari.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) { // daftar pilihan spinner
                var xType = ""
                when (position) {
                    1 -> xType = "mosque"
                    2 -> xType = "restaurant"
                    3 -> xType = "atm"
                    4 -> xType = "bank"
                    5 -> xType = "school"
                    6 -> xType = "hospital"
                    7 -> xType = "laundry"
                    8 -> xType =
                        "university"
                    9 -> xType = "post_office"
                    10 -> xType =
                        "police"
                }
                if (position != 0) { //place API
                    val sb = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                            "location=" + mLatitude + "," + mLongitude +
                            "&radius=5000" +
                            "&types=" + xType +
                            "&sensor=true" +
                            "&key=" + apiKey
                    startProg()
                    PlacesTask().execute(sb)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun initMap() {
        TODO("Not yet implemented")
    }
}