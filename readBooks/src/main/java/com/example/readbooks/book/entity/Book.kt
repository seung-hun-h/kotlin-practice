package com.example.readbooks.book.entity

import org.springframework.data.annotation.Id

class Book(
	@Id
	var id: Long?,
	val title: String,
	val summary: String,
	val writer: String,
	val isbn: String,
	count: Int
) {
	constructor(
		title: String,
		summary: String,
		writer: String,
		isbn: String,
		count: Int
	): this(
		id = null,
		title = title,
		summary = summary,
		writer = writer,
		isbn = isbn,
		count = count
	)

	var count = count
		private set

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Book

		if (title != other.title) return false
		if (summary != other.summary) return false
		if (writer != other.writer) return false
		if (isbn != other.isbn) return false
		if (id != other.id) return false

		return true
	}

	override fun hashCode(): Int {
		var result = title.hashCode()
		result = 31 * result + summary.hashCode()
		result = 31 * result + writer.hashCode()
		result = 31 * result + isbn.hashCode()
		result = 31 * result + (id?.hashCode() ?: 0)
		return result
	}
}
