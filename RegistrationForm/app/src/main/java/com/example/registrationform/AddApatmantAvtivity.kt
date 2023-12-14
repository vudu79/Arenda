package com.example.registrationform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.registrationform.databinding.AddApatmantAvtivityBinding


class AddApatmantAvtivity : AppCompatActivity() {
    lateinit var binding: AddApatmantAvtivityBinding
    private var state = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddApatmantAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun onClickDone(view: View) {
        val intent = Intent()

        if (binding.etName.text.toString().isNotEmpty()) {
            val sq: String = binding.etSquare.text.toString()
            val sqF: Float? = sq.toFloatOrNull()
            val apartmant = Apartmant(
                binding.etName.text.toString(),
                binding.tAddress.text.toString(),
                sqF,
                binding.etCadNamb.text.toString()
            )

            intent.putExtra(Constants.NEW_APATMANT, apartmant)
            setResult(RESULT_OK, intent)
            finish()
        } else {
            val toast = Toast.makeText(
                applicationContext,
                "Заполните обязательные поля!",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }

}