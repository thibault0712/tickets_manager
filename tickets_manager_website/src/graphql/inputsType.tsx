export type UserInput = {
    firstName: String
    lastName: String
    email: String
    phone: String | undefined
}

export type TicketInput = {
    used: Boolean
    userUuid: String | undefined
}