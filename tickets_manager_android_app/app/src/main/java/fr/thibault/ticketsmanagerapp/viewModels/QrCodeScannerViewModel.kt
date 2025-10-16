package fr.thibault.ticketsmanagerapp.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import fr.thibaul.ticketsmanagerapp.type.TicketInput
import fr.thibault.ticketsmanagerapp.Screens
import fr.thibault.ticketsmanagerapp.models.User
import fr.thibault.ticketsmanagerapp.repository.ApiManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QrCodeScannerViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val _ticketStatus = MutableStateFlow<Screens?>(Screens.qrCodeScanner)
    val ticketStatus: StateFlow<Screens?> = _ticketStatus

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user;

    private val _warningMessage = MutableStateFlow<String>("Erreur inconnue")
    val warningMessage: StateFlow<String> = _warningMessage

    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .enableAutoZoom()
        .build()

    private val scanner = GmsBarcodeScanning.getClient(application.applicationContext, options)

    fun startScan() {
        scanner.startScan()
            .addOnSuccessListener {
                barcode -> ticketIsAuthorized(barcode.displayValue);
            }
            .addOnCanceledListener {
                _warningMessage.value = "Scan annulé !"
                _ticketStatus.value = Screens.scanWarning
            }
            .addOnFailureListener { e ->
                _warningMessage.value = e.message.toString()
                _ticketStatus.value = Screens.scanWarning
                Log.e("QrScanner", e.message.toString())
            }
    }

    private fun ticketIsAuthorized(ticketUuid: String?) {

        if (ticketUuid == null) {

        } else {
            viewModelScope.launch {
                val ticketInformation = ApiManager().getTicketInfo(ticketUuid)
                _user.value = ticketInformation?.user

                _ticketStatus.value = if (ticketInformation?.used == false) {

                    ApiManager().updateTicket(ticketUuid, TicketInput(
                        used = true
                    ))

                    Screens.scanSuccess
                } else {
                    Screens.scanError
                }
            }
        }


    }

    fun resetStatus() {
        _ticketStatus.value = Screens.qrCodeScanner
    }
}