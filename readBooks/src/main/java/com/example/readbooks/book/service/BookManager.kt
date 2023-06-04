package com.example.readbooks.book.service

import com.example.readbooks.book.repository.BookRepository
import com.example.readbooks.book.entity.Book
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Transactional(readOnly = true)
@Service
open class BookManager(
	private val bookRepository: BookRepository
) {
	@Transactional
	open fun save(book: Book) {
		bookRepository.save(book)
	}

	open fun findAll(): List<Book> {
		return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
			.collect(Collectors.toList())
	}

	open fun findById(id: Long): Book {
		return bookRepository.findById(id)
			.orElseThrow { NoSuchElementException("no book. id = $id") }
	}

	open fun findAllByTitle(title: String): List<Book> {
		return bookRepository.findBooksByTitle(title)
	}

	@Transactional
	open fun deleteById(id: Long) {
		bookRepository.deleteById(id)
	}
}