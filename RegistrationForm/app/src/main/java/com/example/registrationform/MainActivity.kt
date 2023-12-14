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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registrationform.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  vm: MainViewModel

    private lateinit var bindingClass: ActivityMainBinding

    lateinit var apatmant: Apartmant
    var apatmantsList = ArrayList<Apartmant>()
    lateinit var adapter: ApartmentRCAdapter

    private var addApatmantLauncher: ActivityResultLauncher<Intent>? = null
    private var calecdarLauncher: ActivityResultLauncher<Intent>? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = ApartmentRCAdapter()

        bindingClass.apply {
            rvApatmans.layoutManager = LinearLayoutManager(this@MainActivity)
            rvApatmans.adapter = adapter

        }

        adapter.onItemClick = { apat ->

            intent = Intent(this, CalendarActivity::class.java)
            addApatmantLauncher?.launch(intent)
            Log.d("myTag", apat.name)
        }

        addApatmantLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    apatmant =
                        result.data!!.getSerializableExtra(Constants.NEW_APATMANT) as Apartmant
                    apatmantsList.add(apatmant)
                    Log.d("myTag", "$apatmantsList")
                    adapter.apatmans =apatmantsList

                }
            }

        calecdarLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    Log.d("myTag", ";asdjfkasjdfak.sjhd")


                }
            }


    }

    fun onClickAddAppatmant(view: View) {
        intent = Intent(this, AddApatmantAvtivity::class.java)
        addApatmantLauncher?.launch(intent)
    }

}