package com.example.registrationform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.registrationform.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
    }

    fun onClickSignUp(view: View){
        var intent = Intent(this, RegisterAvtivity::class.java)
        intent.putExtra(Constants.SING_STATE, Constants.SING_UP)
        startActivity(intent)
    }

    fun onClickSignIn(view: View){

    }
}