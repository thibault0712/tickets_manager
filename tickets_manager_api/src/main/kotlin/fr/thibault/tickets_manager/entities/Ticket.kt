package fr.thibault.tickets_manager.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "Tickets")
class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID? = null

    @Column(nullable = false)
    var used: Boolean = false

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    lateinit var user: User
}