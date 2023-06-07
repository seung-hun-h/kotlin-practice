package com.example.readbooks.book

import com.example.readbooks.book.entity.Book
import com.example.readbooks.book.service.BookService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class BookServiceTest(
	private val sut: BookService
): ShouldSpec({
	context("save") {
		should("book should be saved") {
			val expected = Book(
				title = "title",
				summary = "summary",
				writer = "writer",
				isbn = "isbn",
				count = 1
			)

			sut.save(expected)

			val result = sut.getById(expected.id!!)

			result shouldBe result
		}
	}

	context("deleteById") {
		should("book should be deleted") {
			val expected = Book(
				title = "title",
				summary = "summary",
				writer = "writer",
				isbn = "isbn",
				count = 1
			)
			sut.save(expected)

			sut.deleteById(expected.id!!)

			shouldThrow<NoSuchElementException> {
				sut.getById(expected.id!!)
			}

		}
	}
}) 

