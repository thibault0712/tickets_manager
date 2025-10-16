package fr.thibault.ticketsmanagerapp.models

data class Ticket(
    val uuid: String,
    val used: Boolean,
    val user: User
)
