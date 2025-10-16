export type User = {
    uuid: String
    firstName: String
    lastName: String
    email: String
    phone: String | undefined
    tickets: [Ticket] | undefined
}

export type Ticket = {
    uuid: string
    used: Boolean
    user: User
}