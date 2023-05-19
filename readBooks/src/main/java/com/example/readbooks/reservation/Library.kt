package com.example.readbooks.reservation

import com.example.readbooks.book.BookManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional(readOnly = true)
@Service
open class Library(
	private val bookManager: BookManager,
	private val bookReservationRepository: BookReservationRepository
) {

	@Transactional
	open fun reserve(bookId: Long, reservist: String, startAt: LocalDateTime): BookReservation {
		if (bookReservationRepository.existsBookReservationByBookIdAndReservist(bookId, reservist)) {
			throw IllegalArgumentException("Can only rent one copy of the same book at a time")
		}

		val book = bookManager.findById(bookId)

		val bookReservation = book.reserve(reservist, startAt)

		bookManager.save(book)
		bookReservationRepository.save(bookReservation)

		return bookReservation
	}

}