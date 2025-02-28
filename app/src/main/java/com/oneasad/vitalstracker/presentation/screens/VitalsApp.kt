package com.oneasad.vitalstracker.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oneasad.vitalstracker.presentation.screens.components.VitalsAlertDialog
import com.oneasad.vitalstracker.presentation.viewmodel.VitalsViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsApp(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<VitalsViewModel>()

    val vitals by viewModel.vitals.collectAsState()
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var deleteDialog by rememberSaveable { mutableStateOf(false) }
    var isFabVisible by rememberSaveable { mutableStateOf(true) }
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            isFabVisible = backStackEntry.destination.route == "log"
        }
    }

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
                actions = {
                   if(isFabVisible) {
                       IconButton(
                           onClick = { deleteDialog = true }
                       ) {
                           Icon(
                               imageVector = Icons.Default.Delete,
                               contentDescription = "Delete All",
                               tint = Color(0xFF9C4DB9)
                           )
                       }
                   }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEBB9FE),
                    titleContentColor = Color(0xFF9C4DB9)
                )
            )
        },
        floatingActionButton = {
            if(isFabVisible) {
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
            }
        },
        modifier = modifier
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "log"
        ) {
            composable("log") {
                VitalsLogScreen(
                    vitals = vitals,
                    navigateToEditScreen = { vital ->
                        navController.navigate("edit/${vital.vitalRecordId}")
                    },
                    modifier = Modifier.padding(padding)
                )
            }
            composable(
                route = "edit/{vitalId}",
                arguments = listOf(navArgument("vitalId") { type = NavType.IntType })
            ) { backStackEntry ->
                val vitalId = backStackEntry.arguments?.getInt("vitalId")
                val vital = viewModel.getVitalById(vitalId!!)
                if (vital != null) {
                    VitalsEditScreen(
                        vital = vital,
                        onUpdate = { updatedVital ->
                            viewModel.updateVital(updatedVital)
                            navController.popBackStack()
                        },
                        onDelete = {
                            viewModel.deleteVital(vital)
                            navController.popBackStack()
                        },
                        modifier = Modifier.padding(padding)
                    )
                } else {
                    LaunchedEffect(Unit) {
                        navController.popBackStack()
                    }
                    Text("Vital not found", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
    if (showDialog) {
        VitalsAlertDialog(onDismiss = { showDialog = false }, onSave = viewModel::addVital)
    }
    if (deleteDialog) {
        AlertDialog(
            onDismissRequest = { deleteDialog = false },
            title = { Text("Delete All Records") },
            text = { Text("Are you sure you want to delete all vitals? This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteAllVitals()
                        deleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C4DB9))
                ) {
                    Text("Delete All", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { deleteDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9C4DB9),
                        contentColor = Color.White
                    )
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
