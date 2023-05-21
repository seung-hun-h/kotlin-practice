package com.example.readbooks.shell

import com.example.readbooks.book.Book
import com.example.readbooks.reservation.BookReservation

fun convert(book: Book): String {
	return String.format(
		"Book [id=%d, title='%s', summary='%s', writer='%s', isbn='%s']",
		book.id, book.title, book.summary, book.writer, book.isbn
	)
}

fun convert(books: List<Book>): String {
	return books.joinToString("\n") { convert(it) }
}
