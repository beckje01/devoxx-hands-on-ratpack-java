import static ratpack.groovy.Groovy.ratpack
import static ratpack.registry.Registry.single

ratpack {
  handlers {
    register single(DefaultBookService.instance)

    prefix("book/:isbn") {
      all { BookService bookService ->
        String isbn = allPathTokens["isbn"]
        Book b = bookService.getBook(isbn)

        next(single(b))
      }

      get("title") {
        response.send context.get(Book).title
      }

      get("author") { Book b ->
        response.send b.author
      }
    }
  }
}
