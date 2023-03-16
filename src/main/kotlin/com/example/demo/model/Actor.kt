package com.example.demo.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Actor (
    @Id @GeneratedValue
    @Column(name="actor_id", nullable=false, unique=true)
    @get: NotBlank
    val actorID: Long = 0,

    @Column(name="first_name")
    @get: NotBlank
    val firstName: String = "",

    @Column(name="last_name")
    @get: NotBlank
    val lastName: String = ""
)