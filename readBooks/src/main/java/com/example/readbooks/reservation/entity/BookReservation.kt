package com.example.readbooks.reservation.entity

import org.springframework.data.annotation.Id
import java.time.LocalDate
import java.time.Period

class BookReservation(
	val bookId: Long,
	val reservist: String,
	var reservationStatus: ReservationStatus,
	val startAt: LocalDate,
) {
	@Id
	var id: Long? = null
	var extendedCount = 0
	var endAt: LocalDate? = null

	companion object {
		val MAX_RENDER_DURATION = Period.of(0, 0, 7)
		val EXTENDED_DURATION = Period.of(0, 0, 7)
		val MAX_EXTENED_COUNT = 3
	}

	fun renderUntil(endAt: LocalDate) {
		if (reservationStatus != ReservationStatus.RESERVED) {
			throw IllegalStateException("BookReservation is reserved first. ReservationStatus=$reservationStatus")
		}

		if (endAt.isBefore(startAt)) {
			throw IllegalArgumentException("endAt should after startAt. startAt=$startAt, endAt=$endAt")
		}

		this.endAt = endAt
		this.reservationStatus = ReservationStatus.RESERVED
	}

}