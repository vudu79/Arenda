package com.example.registrationform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.registrationform.databinding.RegisterAvtivityBinding

class RegisterAvtivity : AppCompatActivity() {
    lateinit var binding: RegisterAvtivityBinding
    private var state = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        state = intent.getStringExtra(Constants.SING_STATE)!!

        binding = RegisterAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.tvState.text = when (state){
//            Constants.SING_IN -> "Авторизация"
//            Constants.SING_UP -> "Регистрация"
//            else -> ""
//        }

        when (state) {
            Constants.SING_IN -> {
                binding.tvState.text = "Авторизация"
                binding.etName.visibility = View.GONE
                binding.etLastName.visibility = View.GONE
            }
            Constants.SING_UP -> {
                binding.tvState.text = "Регистрация"
            }
        }

    }

    fun onClickDone(view: View) {
        val intent = Intent()
        when (state) {
            Constants.SING_UP -> {
                if (binding.etLogin.text.toString().isNotEmpty()
                    && binding.edPass.text.toString().isNotEmpty()
                    && binding.etName.text.toString().isNotEmpty()
                    && binding.etLastName.text.toString().isNotEmpty()
                ) {
                    Log.d("myTag", binding.etLogin.text.toString())
                    val user = User(
                        binding.etLogin.text.toString(),
                        binding.edPass.text.toString(),
                        binding.etName.text.toString(),
                        binding.etLastName.text.toString()
                    )
                    intent.putExtra(Constants.SING_STATE, Constants.SING_UP)
                    intent.putExtra(Constants.USER, user)
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Все поля должны быть заполнены!",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            }


            Constants.SING_IN -> {
                if (binding.etLogin.text.toString().isNotEmpty()
                    && binding.edPass.text.toString().isNotEmpty()
                ){
                    val creds = Creds(binding.etLogin.toString(), binding.edPass.toString())
                    intent.putExtra(Constants.SING_STATE, Constants.SING_IN)
                    intent.putExtra(Constants.CREDS, creds)
                    setResult(RESULT_OK, intent)
                    finish()
                }else{
                    val toast = Toast.makeText(
                        applicationContext,
                        "Все поля должны быть заполнены!",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }

            }
        }


    }
}