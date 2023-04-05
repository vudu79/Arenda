package com.example.registrationform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.SELECTION_MODE_MULTIPLE
import android.widget.CalendarView
import com.example.registrationform.databinding.ActivityCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*


class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


//        binding.mcv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE)
//        (1..5).forEach { day ->
//            (binding.mcv.setDateSelected(
//                CalendarDay.from(2023, 4, day),
//                true
//            ))
//        }


    }

}

