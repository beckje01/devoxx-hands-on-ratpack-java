import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bind BookRepository, DefaultBookRepository
    bind BookService, DefaultBookService
  }

  handlers {
    prefix("book/:isbn") {
      all { BookService bookService ->
        String isbn = allPathTokens["isbn"]
        Book b = bookService.getBook(isbn)

        next(single(b))
      }

      get("title") { Book b ->
        response.send b.title
      }

      get("author") { Book b ->
        response.send b.author
      }
    }
  }
}
