package fr.thibault.tickets_manager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TicketsManagerApplication

fun main(args: Array<String>) {
	runApplication<TicketsManagerApplication>(*args)
}
