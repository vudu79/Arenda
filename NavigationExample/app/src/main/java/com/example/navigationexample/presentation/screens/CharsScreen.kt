package com.example.navigationexample.presentation.screens//package com.example.navigationexample.presentation.screens


import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.components.Legends
import co.yml.charts.common.extensions.getMaxElementInYAxis
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.GroupBarChart
import co.yml.charts.ui.barchart.StackedBarChart
import co.yml.charts.ui.barchart.models.*
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.utils.proportion


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharsScreen(

) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(content = {
            items(2) { item ->
                when (item) {

                    0 -> {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = "11",
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            SimplePiechart()
                        }
                    }
                    1 -> {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = "11",
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            PiechartWithSliceLables()
                        }
                    }

                }
            }
        })
    }

}



@Composable
private fun SimplePiechart() {
    val pieChartData = DataUtils.getPieChartData()
    val pieChartConfig =
        PieChartConfig(
            labelVisible = true,
            activeSliceAlpha = .9f,
            isEllipsizeEnabled = true,
            sliceLabelEllipsizeAt = TextUtils.TruncateAt.MIDDLE,
            isAnimationEnable = true,
            chartPadding = 30,
            backgroundColor = Color.Black,
            showSliceLabels = false,
            animationDuration = 1500
        )
    Column(modifier = Modifier.height(500.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Legends(legendsConfig = DataUtils.getLegendsConfigFromPieChartData(pieChartData, 3))
        PieChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            pieChartData,
            pieChartConfig
        ) { slice ->
            Log.d("sdf", "$slice")
        }
    }
}


/**
 * Piechart with slice lables
 *
 * @param context
 */
@Composable
private fun PiechartWithSliceLables() {
    val pieChartData = DataUtils.getPieChartData2()

    val pieChartConfig =
        PieChartConfig(
            activeSliceAlpha = .9f,
            isEllipsizeEnabled = true,
            sliceLabelEllipsizeAt = TextUtils.TruncateAt.MIDDLE,
            sliceLabelTypeface = Typeface.defaultFromStyle(Typeface.ITALIC),
            isAnimationEnable = true,
            chartPadding = 20,
            showSliceLabels = true,
            labelVisible = true
        )
    Column(modifier = Modifier.height(500.dp)) {
        Legends(legendsConfig = DataUtils.getLegendsConfigFromPieChartData(pieChartData, 3))
        PieChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            pieChartData,
            pieChartConfig
        ) { slice ->
            Log.d("sdf", "$slice")
        }
    }
}
