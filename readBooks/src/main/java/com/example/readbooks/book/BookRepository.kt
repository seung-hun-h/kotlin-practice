package com.example.readbooks.book

import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<Book, Long> {
	fun findAllByTitle(title: String): List<Book>
}