package fr.thibault.ticketsmanagerapp.repository

import com.apollographql.apollo.ApolloClient
import fr.thibaul.ticketsmanagerapp.TicketByUuidQuery
import fr.thibaul.ticketsmanagerapp.UpdateTicketMutation
import fr.thibaul.ticketsmanagerapp.type.TicketInput
import fr.thibault.ticketsmanagerapp.models.Ticket
import fr.thibault.ticketsmanagerapp.models.User

class ApiManager {

    var apolloClient: ApolloClient = ApolloClient.Builder()
        .serverUrl("http://10.176.21.210:8080/graphql")
        .build();

    suspend fun getTicketInfo(ticketUuid: String): Ticket? {
        val response = apolloClient.query(query = TicketByUuidQuery(uuid = ticketUuid)).execute();

        val data = response.data ?: return null
        val ticket = data.ticketByUuid ?: return null

        return Ticket(
            uuid = ticket.uuid,
            used = ticket.used,
            user = User(
                uuid = ticket.user.uuid,
                firstName = ticket.user.firstName,
                lastName = ticket.user.lastName,
                email = ticket.user.email,
                phone = null,
                tickets = null
            )
        )

    }

    suspend fun updateTicket(ticketUuid: String, ticketInput: TicketInput) {
        val response = apolloClient.mutation(UpdateTicketMutation(ticketUuid, ticketInput)).execute()

    }


}