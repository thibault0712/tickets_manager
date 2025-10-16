import { gql } from "@apollo/client";

export const GET_USER = gql`
  query userByEmail($email: String!) {
    userByEmail(email: $email) {
      uuid
      firstName
      lastName
      phone
      email
    }
  }
`;

export const ADD_USER = gql`
  mutation addUser($userInput: UserInput!) {
    addUser(userInput: $userInput) {
      uuid
      firstName
      lastName
      phone
      email
    }
  }
`;

export const ADD_TICKET = gql`
  mutation addTicket($ticketInput: TicketInput!) {
    addTicket(ticketInput: $ticketInput) {
      uuid
      used
    }
  }
`