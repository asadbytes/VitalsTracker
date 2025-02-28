package com.oneasad.vitalstracker.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oneasad.vitalstracker.data.model.Vital
import com.oneasad.vitalstracker.ui.theme.VitalsTrackerTheme

@Composable
fun VitalsEditScreen(
    vital: Vital,
    onUpdate: (Vital) -> Unit,
    onDelete: (Vital) -> Unit,
    modifier: Modifier = Modifier
) {
    var sys by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }
    var hr by remember { mutableStateOf("") }
    var wt by remember { mutableStateOf("") }
    var bk by remember { mutableStateOf("") }

    LaunchedEffect(vital) {
        sys = vital.systolic.toString()
        dia = vital.diastolic.toString()
        hr = vital.heartRate.toString()
        wt = vital.weight.toString()
        bk = vital.babyKicks.toString()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Input fields using VitalsInputScreen
        VitalsInputScreen(
            sys = sys,
            onSysChange = { sys = it },
            dia = dia,
            onDiaChange = { dia = it },
            hr = hr,
            onHrChange = { hr = it },
            wt = wt,
            onWtChange = { wt = it },
            bk = bk,
            onBkChange = { bk = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    val updatedVital = Vital(
                        vitalRecordId = vital.vitalRecordId,
                        systolic = sys.toIntOrNull() ?: 0,
                        diastolic = dia.toIntOrNull() ?: 0,
                        heartRate = hr.toIntOrNull() ?: 0,
                        weight = wt.toFloatOrNull() ?: 0f,
                        babyKicks = bk.toIntOrNull() ?: 0,
                    )
                    onUpdate(updatedVital)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C4DB9))
            ) {
                Text("Update", color = Color.White)
            }

            Button(
                onClick = { onDelete(vital) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C4DB9))
            ) {
                Text("Delete", color = Color.White)
            }
        }
    }
}

@Preview
@Composable
private fun VitalsEditPreview() {
    VitalsTrackerTheme {
        VitalsEditScreen(
            vital = Vital(
                vitalRecordId = 1,
                systolic = 120,
                diastolic = 80,
                heartRate = 70,
                weight = 50f,
                babyKicks = 2
            ),
            onUpdate = {},
            onDelete = {}
        )
    }
}