package com.example.readbooks.reservation

import com.example.readbooks.book.entity.Book
import com.example.readbooks.book.repository.BookRepository
import com.example.readbooks.reservation.entity.BookReservation
import com.example.readbooks.reservation.entity.ReservationStatus
import com.example.readbooks.reservation.service.Library
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional
@SpringBootTest
open class LibraryTest(
	private val sut: Library,
	private val bookRepository: BookRepository
): ShouldSpec({
	val book = Book(
		title = "title",
		summary = "summary",
		writer = "writer",
		isbn = "isbn",
		count = 1
	)

	 beforeTest {
		 bookRepository.save(book)
	 }


	context("reserve") {
		should("returned BookReservation") {
			val startAt = LocalDate.now()

			val reservation = sut.reserve(book.id!!, "nt11906", startAt)

			reservation.bookId shouldBe book.id!!
			reservation.startAt shouldBe startAt
			reservation.endAt shouldBe null
			reservation.reservationStatus shouldBe ReservationStatus.RESERVED
			reservation.extendedCount shouldBe 0
		}

		should("cannot reserve same book") {
			val startAt = LocalDate.now()
			sut.reserve(book.id!!, "nt11906", startAt)

			shouldThrow<Exception> {
				sut.reserve(book.id!!, "nt11906", startAt)
			}

		}
	}
	context("render") {
		should("render a book successfully") {
			// Given
			val bookId = book.id!!
			val reservist = "John Doe"
			val startAt = LocalDate.now()

			// When
			val result = sut.render(bookId, reservist, startAt)

			// Then
			val savedBook = bookRepository.findById(bookId).get()
			savedBook.count shouldBe 0
			result.reservationStatus shouldBe ReservationStatus.RESERVED
			result.endAt shouldBe startAt.plus(BookReservation.MAX_RENDER_DURATION)
		}
	}
})