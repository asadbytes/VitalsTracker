package com.oneasad.vitalstracker.presentation.screens.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oneasad.vitalstracker.data.model.Vital
import com.oneasad.vitalstracker.presentation.screens.VitalsInputScreen

@Composable
fun VitalsAlertDialog(
    onDismiss: () -> Unit,
    onSave: (Vital) -> Unit
) {
    var sys by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }
    var hr by remember { mutableStateOf("") }
    var wt by remember { mutableStateOf("") }
    var bk by remember { mutableStateOf("") }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        val systolic = sys.toIntOrNull() ?: 0
                        val diastolic = dia.toIntOrNull() ?: 0
                        val heartRate = hr.toIntOrNull() ?: 0
                        val weight = wt.toFloatOrNull() ?: 0f
                        val babyKicks = bk.toIntOrNull() ?: 0
                        if (systolic > 0 && diastolic > 0 && heartRate > 0 && weight > 0 && babyKicks >= 0) {
                            onSave(
                                Vital(
                                    systolic = systolic,
                                    diastolic = diastolic,
                                    heartRate = heartRate,
                                    weight = weight,
                                    babyKicks = babyKicks
                                )
                            )
                            onDismiss()
                        } else {
                            Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9C4DB9),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Submit",
                        fontSize = 22.sp
                    )
                }
            }
        },
        text = {
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
        }
    )
}