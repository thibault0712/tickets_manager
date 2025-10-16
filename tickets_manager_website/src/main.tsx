import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { ApolloClient, HttpLink, InMemoryCache } from "@apollo/client";
import { ApolloProvider } from "@apollo/client/react";

const API_IP = import.meta.env.VITE_API_IP || "localhost";
const API_PORT = import.meta.env.VITE_API_PORT || "8080";

// Création de l'instance apollo
const client = new ApolloClient({

  link: new HttpLink({ uri: "http://" + API_IP + ":" + API_PORT + "/graphql" }),

  cache: new InMemoryCache(),

});


createRoot(document.getElementById('root')!).render(

  <StrictMode>
    <ApolloProvider client={client}>
      <App />
    </ApolloProvider>
  </StrictMode>,
)
