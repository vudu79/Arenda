package com.example.registrationform

import android.content.Intent
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        adapter = ApartmentRCAdapter(apatmantsList)
        bindingClass.apply {
            rvApatmans.layoutManager = LinearLayoutManager(this@MainActivity)
            rvApatmans.adapter = adapter

        }

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    apatmant =
                        result.data!!.getSerializableExtra(Constants.NEW_APATMANT) as Apartmant
                    apatmantsList.add(apatmant)
                    adapter.notifyDataSetChanged()
                }
            }
    }

    fun onClickAddAppatmant(view: View) {
        intent = Intent(this, AddApatmantAvtivity::class.java)
        launcher?.launch(intent)
    }

}