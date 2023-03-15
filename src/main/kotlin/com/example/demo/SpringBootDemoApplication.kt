package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository



@Service
class MessageService(val db: MessageRepository) {
	fun findMessages(): List<Message> = db.findAll().toList()

	fun findMessageById(id: String): List<Message> = db.findById(id).toList()

	fun save(message: Message) {
		db.save(message)
	}

	fun <T : Any> Optional<out T>.toList(): List<T> =
		if (isPresent) listOf(get()) else emptyList()
}

@SpringBootApplication
class SpringBootDemoApplication

fun main(args: Array<String>) {
	runApplication<SpringBootDemoApplication>(*args)
}

@RestController
class MessageController(val service: MessageService) {
	@CrossOrigin(origins = ["http://localhost:5173"])
	@GetMapping("/")
	fun index(): List<Message> = service.findMessages()

	@GetMapping("/{id}")
	fun index(@PathVariable id: String): List<Message> =
		service.findMessageById(id)

	@PostMapping("/")
	fun post(@RequestBody message: Message) {
		service.save(message)
	}
}

@Table("MESSAGES")
data class Message(@Id var id: String?, val text: String)

interface MessageRepository : CrudRepository<Message, String>
