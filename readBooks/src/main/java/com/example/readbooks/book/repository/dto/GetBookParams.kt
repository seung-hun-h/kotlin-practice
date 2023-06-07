package com.example.readbooks.book.repository.dto

import org.hibernate.validator.constraints.Length

data class GetBookParams(
	@field:Length(min = 0, max = 100)
	val title: String?,
	@field:Length(min = 0, max = 30)
	val writer: String?
) {
	private fun isEmpty(): Boolean {
		return title == null &&
				writer == null
	}

	fun validate() {
		if (isEmpty()) {
			throw IllegalArgumentException("At least one parameter must exist.")
		}
	}
}