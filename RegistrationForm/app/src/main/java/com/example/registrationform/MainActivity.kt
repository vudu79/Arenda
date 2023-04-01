package com.example.registrationform

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registrationform.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityMainBinding

    lateinit var apatmant: Apartmant
    var apatmantsList = ArrayList<Apartmant>()
    lateinit var adapter: ApartmentRCAdapter

    private var launcher: ActivityResultLauncher<Intent>? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        adapter = ApartmentRCAdapter()
        bindingClass.apply {
            rvApatmans.layoutManager = LinearLayoutManager(this@MainActivity)
            rvApatmans.adapter = adapter

        }
        adapter.onItemClick = { apat ->

            // do something with your item
            Log.d("myTag", apat.name)
        }

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    apatmant =
                        result.data!!.getSerializableExtra(Constants.NEW_APATMANT) as Apartmant
                    apatmantsList.add(apatmant)
                    Log.d("myTag", "$apatmantsList")
                    adapter.apatmans =apatmantsList

                }
            }


    }

    fun onClickAddAppatmant(view: View) {
        intent = Intent(this, AddApatmantAvtivity::class.java)
        launcher?.launch(intent)
    }

}