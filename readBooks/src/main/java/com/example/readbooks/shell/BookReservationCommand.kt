package com.example.readbooks.shell

import com.example.readbooks.reservation.Library
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import java.time.LocalDate

@ShellComponent
class BookReservationCommand(
	private val library: Library
) {
	@ShellMethod(value = "List book reservations", key = ["books rv list --b"])
	fun findBookReservations(
		@ShellOption bookId: Long
	): String {
		return convert(library.findReservations(bookId))
	}

	@ShellMethod(value = "Reserve book", key = ["books rv --b"])
	fun reserveBook(
		@ShellOption bookId: Long,
		@ShellOption reservist: String,
		@ShellOption startAt: LocalDate
	): String {
		library.reserve(bookId, reservist, startAt)
		return "book is reserved"
	}

}