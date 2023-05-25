package com.example.readbooks.shell

import com.example.readbooks.book.Book
import com.example.readbooks.book.BookManager
import org.springframework.shell.component.flow.ComponentFlow
import org.springframework.shell.component.flow.ResultMode
import org.springframework.shell.component.flow.SelectItem
import org.springframework.shell.standard.AbstractShellComponent
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption


@ShellComponent
open class BookCommand(
	private val bookService: BookManager,
	private val componentFlowBuilder: ComponentFlow.Builder
): AbstractShellComponent() {
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

	@ShellMethod(key = ["flow showcase2"], value = "Showcase with options", group = "Flow")
	open fun showcase2(
		@ShellOption(help = "Field1 value", defaultValue = ShellOption.NULL) field1: String?,
		@ShellOption(help = "Field2 value", defaultValue = ShellOption.NULL) field2: String?,
		@ShellOption(help = "Confirmation1 value", defaultValue = ShellOption.NULL) confirmation1: Boolean?,
		@ShellOption(help = "Path1 value", defaultValue = ShellOption.NULL) path1: String?,
		@ShellOption(help = "Single1 value", defaultValue = ShellOption.NULL) single1: String?,
		@ShellOption(help = "Multi1 value", defaultValue = ShellOption.NULL) multi1: List<String>?
	): String? {
		val single1SelectItems: MutableMap<String, String> = HashMap()
		single1SelectItems["key1"] = "value1"
		single1SelectItems["key2"] = "value2"
		val multi1SelectItems: List<SelectItem> = listOf(
			SelectItem.of("key1", "value1"),
			SelectItem.of("key2", "value2"), SelectItem.of("key3", "value3")
		)
		val multi1ResultValues = multi1 ?: ArrayList()
		val flow = componentFlowBuilder.clone().reset()
			.withStringInput("field1")
			.name("Field1")
			.defaultValue("defaultField1Value")
			.resultValue(field1)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.withStringInput("field2")
			.name("Field2")
			.resultValue(field2)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.withConfirmationInput("confirmation1")
			.name("Confirmation1")
			.resultValue(confirmation1)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.withPathInput("path1")
			.name("Path1")
			.resultValue(path1)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.withSingleItemSelector("single1")
			.name("Single1")
			.selectItems(single1SelectItems)
			.resultValue(single1)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.withMultiItemSelector("multi1")
			.name("Multi1")
			.selectItems(multi1SelectItems)
			.resultValues(multi1ResultValues)
			.resultMode(ResultMode.ACCEPT)
			.and()
			.build()
		val result = flow.run()
		val buf = StringBuilder()
		result.context.stream().forEach { (key, value): Map.Entry<Any?, Any?> ->
			buf.append(key)
			buf.append(" = ")
			buf.append(value)
			buf.append("\n")
		}
		return buf.toString()
	}
}