package fr.thibault.tickets_manager.controllers

import fr.thibault.tickets_manager.entities.User
import fr.thibault.tickets_manager.inputs.UserInput
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
@RequestMapping("/api/users")
@CrossOrigin(origins = ["*"])
class UserController(
    private val userRepository: UserRepository
) {


    @QueryMapping
    fun userByUuid(@Argument uuid: String): User? {
        return try {
            userRepository.findById(UUID.fromString(uuid)).orElse(null)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    @QueryMapping
    fun userByEmail(@Argument email: String): User? {
        return try {
            userRepository.findByEmail(email)
        } catch (e: IllegalArgumentException) {
            null
        }
    }


    @MutationMapping
    fun addUser(@Argument userInput: UserInput): User? {

        val user = User()
        user.firstName = userInput.firstName;
        user.lastName = userInput.lastName;
        user.email = userInput.email;
        user.phone = userInput.phone;

        // Vérification email
        if (!user.email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex())) {
            return null;
        }

        // Vérification prénom
        if (!user.firstName.matches("^[\\p{L} \\-]*$".toRegex())) {
            return null;
        }

        // Vérification du nom
        if (!user.lastName.matches("^[\\p{L} \\-]*$".toRegex())) {
            return null;
        }

        // Vérification du numéro de téléphone
        if (!user.phone?.isEmpty()!! && !user.phone!!.matches("^[+]?[(]?[0-9]{3}[)]?[-s.]?[0-9]{3}[-s.]?[0-9]{4,6}$".toRegex())) {
            return null;
        }

        // Si l'email est déjà utilisé on fait rien
        if (userRepository.findByEmail(email = user.email) != null) {
            return null;
        }

        return userRepository.save(user)

    }


    @MutationMapping
    fun updateUser(@Argument userInput: UserInput, @Argument uuid: String): User? {

        val potentialUser: Optional<User> = userRepository.findById(UUID.fromString(uuid))

        if (potentialUser.isPresent) {

            val user = potentialUser.get()
            user.firstName = userInput.firstName;
            user.lastName = userInput.lastName;
            user.email = userInput.email;
            user.phone = userInput.phone;

            return userRepository.save(user)

        }

        return null

    }


    @MutationMapping
    fun deleteUser(@Argument uuid: String): Boolean {

        val potentialUser: Optional<User> = userRepository.findById(UUID.fromString(uuid))

        if (potentialUser.isPresent) {
            userRepository.delete(potentialUser.get())
            return true
        }

        return false

    }

}