package com.oneasad.vitalstracker.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oneasad.vitalstracker.R
import com.oneasad.vitalstracker.data.model.Vital
import com.oneasad.vitalstracker.ui.theme.lavender
import com.oneasad.vitalstracker.ui.theme.lavenderDark
import com.oneasad.vitalstracker.ui.theme.VitalsTrackerTheme
import com.oneasad.vitalstracker.utils.dateFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun VitalCard(
    vital: Vital,
    modifier: Modifier = Modifier
) {
    val formattedDate = dateFormatter(vital.createdAt)
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(lavender)
                    .weight(1f)
                    .padding(16.dp)
            ) {
                VitalDataGrid(
                    items = listOf(
                        DataCompEntity(R.drawable.heart_rate, "${vital.heartRate} bpm"),
                        DataCompEntity(R.drawable.blood_pressure, "${vital.systolic}/${vital.diastolic} mmHg"),
                        DataCompEntity(R.drawable.scale, "${vital.weight} kg"),
                        DataCompEntity(R.drawable.kick, "${vital.steps} steps")
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
                    .background(lavenderDark)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = formattedDate,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun VitalDataGrid(items: List<DataCompEntity>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            DataComp(icon = item.icon, text = item.text)
        }
    }
}

@Composable
fun DataComp(
    icon: Int,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = lavenderDark,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = lavenderDark,
            fontSize = 16.sp,
            maxLines = 1,
        )
    }
}

data class DataCompEntity(
    val icon: Int,
    val text: String
)

@Preview
@Composable
private fun VitalCardPreview() {
    VitalsTrackerTheme {
        val createdAt = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).parse("2024-01-01") ?: Date()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            VitalCard(
                vital = Vital(
                    systolic = 120,
                    diastolic = 80,
                    heartRate = 70,
                    weight = 50f,
                    vitalRecordId = 1,
                    steps = 1,
                    createdAt = createdAt,
                )
            )
        }
    }
}