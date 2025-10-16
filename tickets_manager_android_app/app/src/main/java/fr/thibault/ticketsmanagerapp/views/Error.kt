package fr.thibault.ticketsmanagerapp.views

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun ErrorInfo(modifier: Modifier = Modifier, viewModel: QrCodeScannerViewModel = viewModel(), navController: NavHostController) {

    val user by viewModel.user.collectAsState()
    val mContext = LocalContext.current
    val mMediaPlayer = MediaPlayer.create(mContext, R.raw.error)

    mMediaPlayer.start()

    val displayName = buildString {
        if (user == null) {
            append(stringResource(id = R.string.error_not_found_title));
            return@buildString;
        }

        append(user!!.lastName + " " + user!!.firstName)
    }

    val errorMessage = buildString {
        if (user == null) {
            append(stringResource(id = R.string.error_not_found_message))
            return@buildString;
        }

        append(stringResource(id = R.string.error_ticket_already_scanned))
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.error_background),
        onClick = {
            viewModel.resetStatus()
            navController.navigate(Screens.qrCodeScanner.route)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = displayName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Icon(
                painter = painterResource(R.drawable.error_icon),
                contentDescription = stringResource(id = R.string.error_icon_description),
                modifier = modifier
                    .size(124.dp),
                tint = Color.White
            )

            Text(
                text = errorMessage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
    }
    
}

@Preview(showBackground = true)
@Composable
fun ErrorInfoTicketAlreadyScannedPreview() {
    TicketsManagerAppTheme {
        ErrorInfo(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorInfoTicketNotFoundedPreview() {
    TicketsManagerAppTheme {
        ErrorInfo(
            navController = rememberNavController()
        )
    }
}