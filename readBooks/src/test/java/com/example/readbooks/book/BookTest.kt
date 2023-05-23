import com.example.readbooks.book.Book
import com.example.readbooks.reservation.ReservationStatus
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class BookTest : ShouldSpec({
	context("reserve") {
		should("reserve a book successfully") {
			val book = createBook(5)
			val reservist = "John"
			val startAt = LocalDate.now()

			val reservation = book.reserve(reservist, startAt)

			reservation.bookId shouldBe book.id
			reservation.reservist shouldBe reservist
			reservation.reservationStatus shouldBe ReservationStatus.RESERVED
			reservation.startAt shouldBe startAt
		}
	}
}) {

	companion object {
		fun createBook(count: Int): Book = Book(
			id = 1L,
			title = "The Great Gatsby",
			summary = "A classic novel about the American Dream",
			writer = "F. Scott Fitzgerald",
			isbn = "9781234567890",
			count = count
		)
	}
}

