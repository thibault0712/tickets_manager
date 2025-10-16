package fr.thibault.ticketsmanagerapp

sealed class Screens(val route: String) {
    object qrCodeScanner: Screens(route = "qrCodeScanner")
    object scanSuccess: Screens(route ="scanSuccess")
    object scanError: Screens(route ="scanError")
    object scanWarning: Screens(route = "scanWarning")
}