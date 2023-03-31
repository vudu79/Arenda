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
    private val adapter = ApartmentRCAdapter()
    private var launcher: ActivityResultLauncher<Intent>? = null
    lateinit var apatmant: Apartmant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    apatmant =
                        result.data!!.getSerializableExtra(Constants.NEW_APATMANT) as Apartmant
                    addApatmant(apatmant)
                }
            }
    }

    fun onClickAddAppatmant(view: View) {
        intent = Intent(this, AddApatmantAvtivity::class.java)
        launcher?.launch(intent)
    }

    private fun addApatmant(ap: Apartmant) {
        bindingClass.apply {
            rvApatmans.layoutManager = LinearLayoutManager(this@MainActivity)
            rvApatmans.adapter = adapter
            adapter.addAppatmant(ap)

        }
    }
}