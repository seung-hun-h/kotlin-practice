import com.example.readbooks.book.Book
import com.example.readbooks.reservation.ReservationStatus
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class BookTest : ShouldSpec({
	context("reserve") {
		should("reserve a book successfully when count is greater than 0") {
			val book = createBook(5)
			val reservist = "John"
			val startAt = LocalDateTime.now()

			val reservation = book.reserve(reservist, startAt)

			reservation.bookId shouldBe book.id
			reservation.reservist shouldBe reservist
			reservation.reservationStatus shouldBe ReservationStatus.RESERVED
			reservation.startAt shouldBe startAt

			book.count shouldBe 4
		}

		should("throw an exception when reserving a book with count 0") {
			val book = createBook(0)
			val reservist = "John"
			val startAt = LocalDateTime.now()

			shouldThrow<IllegalStateException> {
				book.reserve(reservist, startAt)
			}
		}
	}

	context("render") {
		should("render a book successfully when count is greater than 0") {
			val book = createBook(5)
			val reservist = "John"
			val startAt = LocalDateTime.now()

			val reservation = book.render(reservist, startAt)

			reservation.bookId shouldBe book.id
			reservation.reservist shouldBe reservist
			reservation.reservationStatus shouldBe ReservationStatus.RENDERED
			reservation.startAt shouldBe startAt

			book.count shouldBe 4
		}

		should("throw an exception when rendering a book with count 0") {
			val book = createBook(0)

			val reservist = "John"
			val startAt = LocalDateTime.now()

			shouldThrow<IllegalStateException> {
				book.render(reservist, startAt)
			}
		}
	}
}) {

	companion object {
		fun createBook(count: Int): Book = Book(

			title = "The Great Gatsby",
			summary = "A classic novel about the American Dream",
			writer = "F. Scott Fitzgerald",
			isbn = "9781234567890",
			count = count
		)
	}
}

