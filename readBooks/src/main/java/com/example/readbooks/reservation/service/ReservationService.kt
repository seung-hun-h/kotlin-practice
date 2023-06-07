package com.example.readbooks.reservation.service

import com.example.readbooks.book.service.BookService
import com.example.readbooks.reservation.entity.BookReservation
import com.example.readbooks.reservation.entity.ReservationStatus
import com.example.readbooks.reservation.repository.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional(readOnly = true)
@Service
class ReservationService(
	private val bookService: BookService,
	private val reservationRepository: ReservationRepository
) {

	@Transactional
	fun reserve(bookId: Long, reservist: String, startAt: LocalDate): BookReservation {
		if (reservationRepository.existsByBookIdAndReservist(bookId, reservist)) {
			throw IllegalArgumentException("$reservist already reserved that book. bookId=$bookId")
		}

		if (reservationRepository.existsByBookIdAndStartAtAndReservationStatusIsIn(bookId, startAt, listOf(ReservationStatus.RESERVED, ReservationStatus.RENDERED))) {
			throw IllegalStateException("Book[id=$bookId] is already reserved at $startAt. reservist=$reservist")
		}

		val book = bookService.getById(bookId)

		return reservationRepository.save(book.reserve(reservist, startAt))
	}

	@Transactional
	fun render(bookId: Long, reservist: String, startAt: LocalDate): BookReservation {
		var bookReservation = reservationRepository.findByBookIdAndReservistAndStartAtAndReservationStatus(bookId, reservist, startAt, ReservationStatus.RESERVED)

		if (bookReservation == null) {
			bookReservation = reserve(bookId, reservist, startAt)
		}

		val book = bookService.getById(bookId)
		book.render(bookReservation, BookReservation.MAX_RENDER_DURATION)

		bookService.save(book)
		return reservationRepository.save(bookReservation)
	}

	fun findReservations(bookId: Long): List<BookReservation> {
		return reservationRepository.findByBookId(bookId)
	}

}