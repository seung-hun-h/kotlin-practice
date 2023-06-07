package com.example.readbooks.web.book

import com.example.readbooks.book.entity.Book
import com.example.readbooks.book.service.BookService
import com.example.readbooks.book.repository.dto.GetBookParams
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/books")
class BookController(
	private val bookService: BookService
) {
	@GetMapping
	fun getBooks(@Valid request: GetBookParams): ResponseEntity<List<Book>> {
		return ResponseEntity.ok(bookService.get(request))
	}

	@GetMapping("/{id}")
	fun getBook(@PathVariable id: Long): ResponseEntity<Book> {
		return ResponseEntity.ok(bookService.getById(id))
	}

	@DeleteMapping("/{id}")
	fun deleteBook(@PathVariable id: Long): ResponseEntity<Void> {
		bookService.deleteById(id)
		return ResponseEntity.noContent().build()
	}
}