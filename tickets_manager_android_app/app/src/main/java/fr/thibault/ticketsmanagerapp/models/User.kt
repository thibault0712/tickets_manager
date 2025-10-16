package fr.thibault.ticketsmanagerapp.models

data class User(
    val uuid: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String?,
    val tickets: List<Ticket>?,
)
