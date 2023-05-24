package com.example.readbooks.reservation

import com.example.readbooks.book.BookManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional(readOnly = true)
@Service
open class Library(
	private val bookManager: BookManager,
	private val bookReservationRepository: BookReservationRepository
) {

	@Transactional
	open fun reserve(bookId: Long, reservist: String, startAt: LocalDate): BookReservation {
		if (bookReservationRepository.existsByBookIdAndReservist(bookId, reservist)) {
			throw IllegalArgumentException("$reservist already reserved that book. bookId=$bookId")
		}

		if (bookReservationRepository.existsByBookIdAndStartAtAndReservationStatusIsIn(bookId, startAt, listOf(ReservationStatus.RESERVED, ReservationStatus.RENDERED))) {
			throw IllegalStateException("Book[id=$bookId] is already reserved at $startAt. reservist=$reservist")
		}

		val book = bookManager.findById(bookId)

		return bookReservationRepository.save(book.reserve(reservist, startAt))
	}

	@Transactional
	open fun render(bookId: Long, reservist: String, startAt: LocalDate): BookReservation {
		var bookReservation = bookReservationRepository.findByBookIdAndReservistAndStartAtAndReservationStatus(bookId, reservist, startAt, ReservationStatus.RESERVED)

		if (bookReservation == null) {
			bookReservation = reserve(bookId, reservist, startAt)
		}

		val book = bookManager.findById(bookId)
		book.render(bookReservation, BookReservation.MAX_RENDER_DURATION)

		bookManager.save(book)
		return bookReservationRepository.save(bookReservation)
	}

	open fun findReservations(bookId: Long): List<BookReservation> {
		return bookReservationRepository.findByBookId(bookId)
	}

}