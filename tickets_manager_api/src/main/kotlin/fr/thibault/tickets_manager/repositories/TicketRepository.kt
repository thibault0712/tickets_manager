package fr.thibault.tickets_manager.repositories

import fr.thibault.tickets_manager.entities.Ticket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TicketRepository: JpaRepository<Ticket, UUID> {
}