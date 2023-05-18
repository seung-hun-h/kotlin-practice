package com.example.readbooks.book

import org.springframework.data.annotation.Id

class Book(
	val title: String,
	val summary: String,
	val writer: String,
	val isbn: String
) {
	@Id
	var id: Long? = null

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
