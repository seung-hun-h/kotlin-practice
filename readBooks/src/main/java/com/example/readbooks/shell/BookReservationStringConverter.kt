package com.example.readbooks.shell

import com.example.readbooks.reservation.BookReservation

fun convert(bookReservations: List<BookReservation>): String {
	return bookReservations.map {
		String.format("[id=%d, status=%s, period=%s~%s]", it.id, it.reservationStatus, it.startAt, it.endAt)
	}.joinToString { "\n" }
}