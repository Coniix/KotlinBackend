package com.example.demo.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Film (
    @Id @GeneratedValue
    @Column(name="film_id", nullable=false, unique=true)
    @get: NotBlank
    val filmID: Long = 0,

    @Column(name="title")
    @get: NotBlank
    val title: String = "",

    @Column(name="description")
    @get: NotBlank
    val description: String = ""
)