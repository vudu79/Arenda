package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    companion object {
        const val TOTAL_COUNT = "total_count"
    }

    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater) //initializing the binding class
        setContentView(binding.root)
        showRandomNamber()
    }

    fun showRandomNamber(){
        val count = intent.getIntExtra(TOTAL_COUNT, 0)
        val random = java.util.Random()
        var randomInt = 0

        if (count>0){
            randomInt = random.nextInt(count+1)
        }
        binding.textViewRandom.text = randomInt.toString()
        binding.TextViewLebel.text = getString(R.string.random_heading, count)

    }
}