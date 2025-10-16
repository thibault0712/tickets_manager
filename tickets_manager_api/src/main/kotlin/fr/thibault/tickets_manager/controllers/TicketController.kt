package fr.thibault.tickets_manager.controllers

import fr.thibault.tickets_manager.entities.Ticket
import fr.thibault.tickets_manager.entities.User
import fr.thibault.tickets_manager.inputs.TicketInput
import fr.thibault.tickets_manager.repositories.TicketRepository
import fr.thibault.tickets_manager.repositories.UserRepository
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import java.util.Optional
import java.util.UUID

@Controller
@CrossOrigin(origins = ["*"])
@RequestMapping("/api/tickets")
class TicketController(
    private val ticketRepository: TicketRepository,
    private val userRepository: UserRepository
) {

    @QueryMapping
    fun ticketByUuid(@Argument uuid: String): Ticket {
        return ticketRepository.findById(UUID.fromString(uuid)).orElse(null);
    }

    @MutationMapping
    fun addTicket(@Argument ticketInput: TicketInput): Ticket? {

        val userUuid: UUID = UUID.fromString(ticketInput.userUuid)
        val potentialUser: Optional<User> = userRepository.findById(userUuid)

        if (potentialUser.isPresent) {
            val ticket = Ticket()

            ticket.used = ticketInput.used
            ticket.user = potentialUser.get()

            return ticketRepository.save(ticket)
        }

        return null

    }

    @MutationMapping
    fun updateTicket(@Argument ticketInput: TicketInput, @Argument uuid: String): Ticket? {

        val potentialTicket: Optional<Ticket> = ticketRepository.findById(UUID.fromString(uuid))

        if (potentialTicket.isPresent) {

            val ticket = potentialTicket.get()
            ticket.used = ticketInput.used

            if (ticketInput.userUuid != null) {
                val potentialUser: Optional<User> = userRepository.findById(UUID.fromString(ticketInput.userUuid))

                if (potentialUser.isPresent) {
                    ticket.user = potentialUser.get()
                }
            }

            return ticketRepository.save(ticket)

        }

        return null

    }

    @MutationMapping
    fun deleteTicket(@Argument uuid: String): Boolean {

        val potentialTicket: Optional<Ticket> = ticketRepository.findById(UUID.fromString(uuid))

        if (potentialTicket.isPresent) {
            ticketRepository.delete(potentialTicket.get())
            return true
        }

        return false

    }



}