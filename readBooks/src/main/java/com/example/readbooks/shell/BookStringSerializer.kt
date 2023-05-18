package com.example.readbooks.shell

import com.example.readbooks.book.Book

fun serialize(book: Book): String {
	return String.format(
		"Book [id=%d, title='%s', summary='%s', writer='%s', isbn='%s']",
		book.id, book.title, book.summary, book.writer, book.isbn
	)
}

fun serialize(books: List<Book>): String {
	return books.joinToString("\n") { serialize(it) }
}
