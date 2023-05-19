package com.example.readbooks.book

import com.example.readbooks.reservation.BookReservation
import com.example.readbooks.reservation.BookReservationStatus
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

class Book(
	private val title: String,
	private val summary: String,
	private val writer: String,
	private val isbn: String,
	private var count: Int
) {
	@Id
	var id: Long? = null

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Book

		if (title != other.title) return false
		if (summary != other.summary) return false
		if (writer != other.writer) return false
		if (isbn != other.isbn) return false
		if (id != other.id) return false

		return true
	}

	override fun hashCode(): Int {
		var result = title.hashCode()
		result = 31 * result + summary.hashCode()
		result = 31 * result + writer.hashCode()
		result = 31 * result + isbn.hashCode()
		result = 31 * result + (id?.hashCode() ?: 0)
		return result
	}

	fun reserve(reservist: String, startAt: LocalDateTime): BookReservation {
		if (count == 0) {
			throw IllegalStateException("book is not enough")
		}

		count -= 1

		return BookReservation(
			this,
			reservist,
			BookReservationStatus.RESERVED,
			startAt
		)
	}

	fun render(reservist: String, startAt: LocalDateTime): BookReservation {
		if (count == 0) {
			throw IllegalStateException("book is not enough")
		}

		count -= 1

		return BookReservation(
			this,
			reservist,
			BookReservationStatus.RENDERED,
			startAt
		)
	}
}
