package com.example.readbooks.book.service

import com.example.readbooks.book.repository.BookRepository
import com.example.readbooks.book.entity.Book
import com.example.readbooks.book.repository.dto.GetBooksRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class BookService(
	private val bookRepository: BookRepository
) {
	@Transactional
	fun save(book: Book) {
		bookRepository.save(book)
	}

	fun getById(id: Long): Book {
		return bookRepository.findById(id)
			.orElseThrow { NoSuchElementException("no book. id = $id") }
	}

	fun get(request: GetBooksRequest): List<Book> {
		request.validate()
		return bookRepository.findBooks(request)
	}

	@Transactional
	fun deleteById(id: Long) {
		bookRepository.deleteById(id)
	}
}