package com.oneasad.vitalstracker.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oneasad.vitalstracker.presentation.screens.components.VitalCard
import com.oneasad.vitalstracker.presentation.screens.components.VitalsAlertDialog
import com.oneasad.vitalstracker.presentation.viewmodel.VitalsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsLogScreen(viewModel: VitalsViewModel, modifier: Modifier = Modifier) {
    val vitals by viewModel.vitals.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Vitals Tracker",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                        },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEBB9FE),
                    titleContentColor = Color(0xFF9C4DB9)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                shape = RoundedCornerShape(100),
                containerColor = Color(0xFF9C4DB9),
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Vital",
                    tint = Color.White
                )
            }
        },
        modifier = modifier
    ) { padding ->
        if(vitals.isNotEmpty()) {
            Column(Modifier.padding(padding).padding(8.dp)) {
                LazyColumn {
                    items(vitals) { vital ->
                        VitalCard(vital = vital)
                    }
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text(
                    text = "No vitals yet",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    if (showDialog) {
        VitalsAlertDialog(onDismiss = { showDialog = false }, onSave = viewModel::addVital)
    }
}

