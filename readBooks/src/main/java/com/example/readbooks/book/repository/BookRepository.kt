package com.example.readbooks.book.repository

import com.example.readbooks.book.entity.Book
import com.example.readbooks.book.repository.dto.GetBookParams
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<Book, Long> {
	fun findBooksByTitle(title: String): List<Book>
	fun findBooks(request: GetBookParams): List<Book>
}