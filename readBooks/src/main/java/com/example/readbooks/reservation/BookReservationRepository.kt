package com.example.readbooks.reservation

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

interface BookReservationRepository: CrudRepository<BookReservation, Long>{
	fun existsBookReservationByBookIdAndReservist(bookId: Long, reservist: String): Boolean

	fun findByBookId(bookId: Long): List<BookReservation>
}