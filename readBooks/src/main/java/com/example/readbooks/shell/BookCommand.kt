package com.example.readbooks.shell

import com.example.readbooks.book.Book
import com.example.readbooks.book.BookManager
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class BookCommand(private val bookService: BookManager) {
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
		@ShellOption(value = ["--t"]) title: String,
		@ShellOption(value = ["--s"]) summary: String,
		@ShellOption(value = ["--w"]) writer: String,
		@ShellOption(value = ["--i"]) isbn: String,
		@ShellOption(value = ["--c"], defaultValue = "1") count: Int
	) {
		val book = Book(title, summary, writer, isbn, count)
		bookService.save(book)
	}
}