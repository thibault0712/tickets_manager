package fr.thibault.ticketsmanagerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apollographql.apollo.ApolloClient
import fr.thibaul.ticketsmanagerapp.TicketByUuidQuery
import fr.thibault.ticketsmanagerapp.models.User
import fr.thibault.ticketsmanagerapp.ui.theme.TicketsManagerAppTheme
import fr.thibault.ticketsmanagerapp.viewModels.QrCodeScannerViewModel
import fr.thibault.ticketsmanagerapp.views.ErrorInfo
import fr.thibault.ticketsmanagerapp.views.QrCodeScannerScreen
import fr.thibault.ticketsmanagerapp.views.SuccessInfo
import fr.thibault.ticketsmanagerapp.views.WarnInfo
import kotlin.getValue

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            TicketsManagerAppTheme {

                val viewModel: QrCodeScannerViewModel = viewModel();

                val navController = rememberNavController();

                NavHost(
                    navController = navController,
                    startDestination = Screens.qrCodeScanner.route
                ) {
                    composable(Screens.qrCodeScanner.route) {
                        QrCodeScannerScreen(viewModel = viewModel, navController = navController);
                    }

                    composable(Screens.scanSuccess.route) {
                        SuccessInfo(
                            viewModel = viewModel,
                            navController = navController
                        );
                    }

                    composable(Screens.scanError.route) {
                        ErrorInfo(
                            viewModel = viewModel,
                            navController = navController
                        );
                    }

                    composable(Screens.scanWarning.route) {
                        WarnInfo(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }

            }
        }
    }
}

