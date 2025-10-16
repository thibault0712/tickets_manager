package fr.thibault.ticketsmanagerapp.views

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import fr.thibault.ticketsmanagerapp.R
import fr.thibault.ticketsmanagerapp.Screens
import fr.thibault.ticketsmanagerapp.models.User
import fr.thibault.ticketsmanagerapp.ui.theme.TicketsManagerAppTheme
import fr.thibault.ticketsmanagerapp.viewModels.QrCodeScannerViewModel
import kotlinx.coroutines.delay

@Composable
fun SuccessInfo(modifier: Modifier = Modifier, viewModel: QrCodeScannerViewModel = viewModel(), navController: NavHostController) {

    val mContext = LocalContext.current
    val mMediaPlayer = MediaPlayer.create(mContext, R.raw.success)
    val user by viewModel.user.collectAsState()

    mMediaPlayer.start()

    if (user == null) {
        navController.navigate(Screens.scanError.route)
        throw NullPointerException("User could not be null");
    }

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.success_background),
        onClick = {
            viewModel.resetStatus()
            navController.navigate(Screens.qrCodeScanner.route)
        }
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${user!!.lastName} ${user!!.firstName}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Icon(
                painter = painterResource(R.drawable.success_icon),
                contentDescription = stringResource(id = R.string.success_icon_description),
                modifier = modifier
                    .size(124.dp),
                tint = Color.White
            )

            Text(
                text = "Ticket validé",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
    }
    
}

@Preview(showBackground = true)
@Composable
fun SuccessInfoPreview() {
    TicketsManagerAppTheme {
            SuccessInfo(
                navController = rememberNavController()
            )
    }
}