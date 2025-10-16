package fr.thibault.tickets_manager.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "Users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID? = null

    @Column(nullable = false)
    var firstName: String = ""

    @Column(nullable = false)
    var lastName: String = ""

    @Column(nullable = false)
    var email: String = ""

    @Column
    var phone: String? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var tickets: MutableList<Ticket> = mutableListOf()

}