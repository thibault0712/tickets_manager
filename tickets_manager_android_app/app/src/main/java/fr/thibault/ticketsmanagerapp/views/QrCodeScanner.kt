package fr.thibault.ticketsmanagerapp.views

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import fr.thibault.ticketsmanagerapp.Screens
import fr.thibault.ticketsmanagerapp.models.User
import fr.thibault.ticketsmanagerapp.repository.ApiManager
import fr.thibault.ticketsmanagerapp.ui.theme.TicketsManagerAppTheme
import fr.thibault.ticketsmanagerapp.viewModels.QrCodeScannerViewModel

@Composable
fun QrCodeScannerScreen(viewModel: QrCodeScannerViewModel = viewModel(), modifier: Modifier = Modifier, navController: NavHostController) {

    val status by viewModel.ticketStatus.collectAsState()

    if (status?.route != Screens.qrCodeScanner.route && status?.route != null) {
        navController.navigate(status!!.route);
    }

    LaunchedEffect(Unit) {
        viewModel.startScan()
    }
}