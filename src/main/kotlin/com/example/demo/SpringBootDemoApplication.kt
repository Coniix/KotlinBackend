package com.example.demo

import com.example.demo.model.Actor
import com.example.demo.model.Film
import com.example.demo.repository.ActorRepository
import com.example.demo.repository.FilmRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@SpringBootApplication
class SpringBootDemoApplication

fun main(args: Array<String>) {
	runApplication<SpringBootDemoApplication>(*args)
}

@EntityScan("com.example.demo.*")
@RestController
class ActorController(private val actorRepository: ActorRepository) {

	@GetMapping("/actors")
	fun getAllActors(): List<Actor> =
		actorRepository.findAll()


	@PostMapping("/actors")
	fun createNewActor(@Valid @RequestBody actor: Actor): Actor =
		actorRepository.save(actor)


	@GetMapping("/actors/{id}")
	fun getActorById(@PathVariable(value = "id") actorId: Long): ResponseEntity<Actor> {
		return actorRepository.findById(actorId).map { actor ->
			ResponseEntity.ok(actor)
		}.orElse(ResponseEntity.notFound().build())
	}

	@PutMapping("/actors/{id}")
	fun updateActorById(@PathVariable(value = "id") actorId: Long,
						@Valid @RequestBody newActor: Actor
	): ResponseEntity<Actor> {

		return actorRepository.findById(actorId).map { existingActor ->
			val updatedActor: Actor = existingActor
				.copy(firstName = newActor.firstName, lastName = newActor.lastName)
			ResponseEntity.ok().body(actorRepository.save(updatedActor))
		}.orElse(ResponseEntity.notFound().build())

	}

	@DeleteMapping("/actors/{id}")
	fun deleteActorById(@PathVariable(value = "id") actorId: Long): ResponseEntity<Void> {

		return actorRepository.findById(actorId).map { actor  ->
			actorRepository.delete(actor)
			ResponseEntity<Void>(HttpStatus.OK)
		}.orElse(ResponseEntity.notFound().build())

	}
}

@EntityScan("com.example.demo.*")
@RestController
class FilmController(private val filmRepository: FilmRepository) {

	@GetMapping("/films")
	fun getAllFilms(): List<Film> =
		filmRepository.findAll()


	@PostMapping("/films")
	fun createNewFilm(@Valid @RequestBody film: Film): Film =
		filmRepository.save(film)


	@GetMapping("/films/{id}")
	fun getFilmById(@PathVariable(value = "id") filmId: Long): ResponseEntity<Film> {
		return filmRepository.findById(filmId).map { film ->
			ResponseEntity.ok(film)
		}.orElse(ResponseEntity.notFound().build())
	}
}