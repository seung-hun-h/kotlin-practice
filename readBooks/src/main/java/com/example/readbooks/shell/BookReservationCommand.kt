package com.example.readbooks.shell

import com.example.readbooks.reservation.Library
import org.springframework.context.annotation.Profile
import org.springframework.shell.component.flow.ComponentFlow
import org.springframework.shell.component.flow.ResultMode
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import java.time.LocalDate

@Profile("shell")
@ShellComponent
class BookReservationCommand(
	private val library: Library,
	private val componentFlowBuilder: ComponentFlow.Builder
) {
	@ShellMethod(value = "List book reservations", key = ["books reserve list"])
	fun findBookReservations(
		@ShellOption id: Long
	): String {
		return convert(library.findReservations(id))
	}

	@ShellMethod(value = "Reserve book", key = ["books reserve"])
	fun reserveBook(
		@ShellOption(defaultValue = ShellOption.NULL) bookId: String?,
		@ShellOption(defaultValue = ShellOption.NULL) reservist: String?,
		@ShellOption(defaultValue = ShellOption.NULL) afterDays: String?
	): String {
		val flow = componentFlowBuilder.clone().reset()
			.withStringInput("BookId")
			.name("Book's Id")
			.resultValue(bookId)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.withStringInput("Reservist")
			.name("Reservist")
			.resultValue(reservist)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.withStringInput("AfterDays")
			.name("Days later")
			.resultValue(afterDays)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.build()

		val context = flow.run().context

		library.reserve(
			bookId = context.get<String?>("BookId").toLong(),
			reservist = context.get("Reservist"),
			startAt = LocalDate.now().minusDays(context.get<String?>("AfterDays").toLong())
		)
		return "book is reserved"
	}

}