package com.example.readbooks.reservation

import com.example.readbooks.book.Book
import com.example.readbooks.book.BookRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@SpringBootTest
open class LibraryTest(
	private val sut: Library,
	private val bookRepository: BookRepository
): ShouldSpec({
	context("reserve") {
		should("returned BookReservation") {
			val book = Book(
				title = "title",
				summary = "summary",
				writer = "writer",
				isbn = "isbn",
				count = 1
			)
			bookRepository.save(book)
			val startAt = LocalDateTime.now()

			val reservation = sut.reserve(book.id!!, "nt11906", startAt)

			reservation.bookId shouldBe book.id!!
			reservation.startAt shouldBe startAt
			reservation.endAt shouldBe null
			reservation.reservationStatus shouldBe ReservationStatus.RESERVED
			reservation.extendedCount shouldBe 0
		}

		should("cannot reserve same book") {
			val book = Book(
				title = "title",
				summary = "summary",
				writer = "writer",
				isbn = "isbn",
				count = 1
			)
			bookRepository.save(book)
			val startAt = LocalDateTime.now()
			sut.reserve(book.id!!, "nt11906", startAt)

			shouldThrow<Exception> {
				sut.reserve(book.id!!, "nt11906", startAt)
			}

		}
	}
})