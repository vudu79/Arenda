package com.example.navigationexample.presentation.utils

import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.core.yearMonth
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.LazyThreadSafetyMode.NONE


data class DateSelection(val startDate: LocalDate? = null, val endDate: LocalDate? = null) {
    val daysBetween by lazy(NONE) {
        if (startDate == null || endDate == null) null else {
            ChronoUnit.DAYS.between(startDate, endDate)
        }
    }
}

private val rangeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
fun dateRangeDisplayText(startDate: LocalDate, endDate: LocalDate): String {
    return "Selected: ${rangeFormatter.format(startDate)} - ${rangeFormatter.format(endDate)}"
}

object ContinuousSelectionHelper {
    fun getSelection(
        clickedDate: LocalDate,
        dateSelection: DateSelection,
//        planedApaertmenDays: List<LocalDate>
    ): DateSelection {
        val (selectionStartDate, selectionEndDate) = dateSelection

//        val selectionPeriod = listDaysBetween1(
//            startDate = selectionStartDate,
//            endDate = selectionEndDate
//        )

        return if (selectionStartDate != null) {
            if (clickedDate < selectionStartDate || selectionEndDate != null) {
                DateSelection(startDate = clickedDate, endDate = null)
            } else if (clickedDate != selectionStartDate) {

                DateSelection(startDate = selectionStartDate, endDate = clickedDate)
            } else {
                DateSelection(startDate = clickedDate, endDate = null)
            }
        } else {
            DateSelection(startDate = clickedDate, endDate = null)
        }
    }

    fun isInDateBetweenSelection(
        inDate: LocalDate,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Boolean {
        if (startDate.yearMonth == endDate.yearMonth) return false
        if (inDate.yearMonth == startDate.yearMonth) return true
        val firstDateInThisMonth = inDate.yearMonth.nextMonth.atStartOfMonth()
        return firstDateInThisMonth in startDate..endDate && startDate != firstDateInThisMonth
    }

    fun isOutDateBetweenSelection(
        outDate: LocalDate,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Boolean {
        if (startDate.yearMonth == endDate.yearMonth) return false
        if (outDate.yearMonth == endDate.yearMonth) return true
        val lastDateInThisMonth = outDate.yearMonth.previousMonth.atEndOfMonth()
        return lastDateInThisMonth in startDate..endDate && endDate != lastDateInThisMonth
    }
}


fun getDaysBetweenList(
    startDate: LocalDate? = null,
    endDate: LocalDate? = null
): MutableList<LocalDate> {
    val listDays: MutableList<LocalDate> = mutableListOf()
    val numOfDaysBetween = (ChronoUnit.DAYS.between(startDate, endDate)).toInt()
    (0..numOfDaysBetween).forEach {
        val date: LocalDate? = startDate?.plusDays(it.toLong())
        if (date != null) {
            listDays.add(date)
        }
    }
    return listDays
}

fun isRightSelection(selection: DateSelection, rentalList: List<LocalDate>): Boolean {
    if (selection.daysBetween != null) {
        val (startDate, endDate) = selection
        val selectedDatesList = getDaysBetweenList(startDate, endDate)
        val intersectSet = selectedDatesList.intersect(rentalList)
        return intersectSet.isEmpty()
    } else return false
}