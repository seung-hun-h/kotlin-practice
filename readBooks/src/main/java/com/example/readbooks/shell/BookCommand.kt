package com.example.readbooks.shell

import com.example.readbooks.book.Book
import com.example.readbooks.book.BookManager
import org.springframework.context.annotation.Profile
import org.springframework.shell.component.flow.ComponentFlow
import org.springframework.shell.component.flow.ResultMode
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@Profile("shell")
@ShellComponent
open class BookCommand(
	private val bookService: BookManager,
	private val componentFlowBuilder: ComponentFlow.Builder
) {
	@ShellMethod(value = "List books", key = ["books list"])
	fun findAllBooks(): String {
		return convert(bookService.findAll())
	}

	@ShellMethod(value = "Find book by id", key = ["books find -id"])
	fun findBookById(id: Long): String {
		return convert(bookService.findById(id))
	}

	@ShellMethod(value = "Find book by title", key = ["books find -title"])
	fun findBooksByTitle(title: String): String {
		return convert(bookService.findAllByTitle(title))
	}

	@ShellMethod(value = "Register book", key = ["books register"])
	fun registerBook(
		@ShellOption(help = "Book's title", defaultValue = ShellOption.NULL) title: String?,
		@ShellOption(help = "Book's summary", defaultValue = ShellOption.NULL) summary: String?,
		@ShellOption(help = "Book's writer", defaultValue = ShellOption.NULL) writer: String?,
		@ShellOption(help = "Book's isbn", defaultValue = ShellOption.NULL) isbn: String?,
		@ShellOption(help = "Book's inventory", defaultValue = ShellOption.NULL) count: String?
	): String {
		val flow = componentFlowBuilder.clone().reset()
			.withStringInput("Title")
			.name("Title")
			.resultValue(title)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.withStringInput("Summary")
			.name("Summary")
			.resultValue(summary)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.withStringInput("Writer")
			.name("Writer")
			.resultValue(writer)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.withStringInput("Isbn")
			.name("Isbn")
			.resultValue(isbn)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.withStringInput("Count")
			.name("Count")
			.resultValue(count)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.build()

		val context = flow.run().context

		val book = Book (
			title = context.get("Title"),
			summary = context.get("Summary"),
			writer = context.get("Writer"),
			isbn = context.get("Isbn"),
			count = context.get<String?>("Count").toInt()
		)

		bookService.save(book)

		return "Book is saved!!"
	}
}