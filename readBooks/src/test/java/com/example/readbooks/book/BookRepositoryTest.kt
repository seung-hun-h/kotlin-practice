package com.example.readbooks.book

import com.example.readbooks.book.entity.Book
import com.example.readbooks.book.repository.BookRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest

@DataJdbcTest
open class BookRepositoryTest(
	private val sut: BookRepository
): ShouldSpec({
	context("findBooksByTitle") {
		should("returned books that have same title") {
			val book = Book(
				title = "title",
				summary = "summary",
				writer = "writer",
				isbn = "isbn",
				count = 1
			)
			sut.save(book)

			val results = sut.findBooksByTitle("title")

			results shouldHaveSize 1
			results.first() shouldBe book
		}

	}
})