package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //initializing the binding class
        setContentView(binding.root)
    }

    fun toastMe(view: View){
        val toast = Toast.makeText(this, "Thit is a same toast!!!", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun countMe(view: View){
        val textViewString = binding.textView.text.toString()
        var counter: Int = Integer.parseInt(textViewString)
        counter++

        binding.textView.text = counter.toString()

    }

    fun randomMe(view: View){
        val randomIntent = Intent(this@MainActivity, SecondActivity::class.java)

        val countText = binding.textView.text.toString()
        val count = Integer.parseInt(countText)

        randomIntent.putExtra(SecondActivity.TOTAL_COUNT, count)

        startActivity(randomIntent)
    }

}