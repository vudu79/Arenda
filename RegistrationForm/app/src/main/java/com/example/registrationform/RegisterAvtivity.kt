package com.example.registrationform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.registrationform.databinding.RegisterAvtivityBinding

class RegisterAvtivity : AppCompatActivity() {
    lateinit var binding : RegisterAvtivityBinding
    private var state = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        state = intent.getStringExtra(Constants.SING_STATE)!!

        binding = RegisterAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvState.text = when (state){
            Constants.SING_IN -> "Авторизация"
            Constants.SING_UP -> "Регистрация"
            else -> ""
        }
    }
    fun onClickDone(view: View){
        val intent = Intent()
        intent.putExtra(Constants.SING_STATE, Constants.SING_UP)
        setResult(RESULT_OK, intent)
        finish()
    }
}