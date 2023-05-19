package com.example.readbooks.reservation

import com.example.readbooks.book.Book
import org.springframework.data.repository.CrudRepository

interface BookReservationRepository: CrudRepository<BookReservation, Long>{
	fun existsBookReservationByBookIdAndReservist(bookId: Long, reservist: String): Boolean
}