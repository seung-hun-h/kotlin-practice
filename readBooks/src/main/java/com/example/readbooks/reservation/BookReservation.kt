package com.example.readbooks.reservation

import com.example.readbooks.book.Book
import org.springframework.data.annotation.Id
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class BookReservation(
	book: Book,
	val reservist: String,
	val reservationStatus: ReservationStatus,
	val startAt: LocalDateTime,
) {
	constructor(
		book: Book,
		reservist: String,
		reservationStatus: ReservationStatus,
		startAt: LocalDateTime,
		endAt: LocalDateTime
	) : this(book, reservist, reservationStatus, startAt) {
		if (reservationStatus != ReservationStatus.RENDERED) {
			throw IllegalArgumentException("BookReservationStatus should be RENDERED")
		}

		this.endAt = endAt
	}

	@Id
	var id: Long? = null
	var extendedCount = 0
	var endAt: LocalDateTime? = null
	val bookId = book.id

	companion object {
		val MAX_RENDER_DURATION = Duration.of(7, ChronoUnit.DAYS)
		val EXTENDED_DURATION = Duration.of(3, ChronoUnit.DAYS)
		val MAX_EXTENED_COUNT = 3
	}

}