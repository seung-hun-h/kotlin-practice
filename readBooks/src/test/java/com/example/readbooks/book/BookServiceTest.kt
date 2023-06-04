package com.example.readbooks.book

import com.example.readbooks.book.entity.Book
import com.example.readbooks.book.service.BookManager
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
open class BookServiceTest(
	private val sut: BookManager
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

			val result = sut.findById(expected.id!!)

			result shouldBe result
		}
	}

	context("findAllByTitle") {
		should("returned books having same title") {
			val expected = Book(
				title = "title",
				summary = "summary",
				writer = "writer",
				isbn = "isbn",
				count = 1
			)

			sut.save(expected)

			val result = sut.findAllByTitle("title")

			result shouldContain expected
			result shouldHaveSize 1
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
				sut.findById(expected.id!!)
			}

		}
	}
}) 

