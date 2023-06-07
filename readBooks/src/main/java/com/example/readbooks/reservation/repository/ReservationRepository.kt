package com.example.readbooks.reservation.repository

import com.example.readbooks.reservation.entity.BookReservation
import com.example.readbooks.reservation.entity.ReservationStatus
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface ReservationRepository: CrudRepository<BookReservation, Long>{
	fun existsByBookIdAndReservist(bookId: Long, reservist: String): Boolean

	fun existsByBookIdAndStartAtAndReservationStatusIsIn(bookId: Long, startAt: LocalDate, reservationStatus: List<ReservationStatus>): Boolean

	fun findByBookId(bookId: Long): List<BookReservation>

	fun findByBookIdAndReservistAndStartAtAndReservationStatus(bookId: Long, reservist: String, startAt: LocalDate, reserved: ReservationStatus): BookReservation?
}