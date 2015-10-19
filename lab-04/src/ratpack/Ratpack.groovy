import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    /*
    * TODO bind DefaultBookService as the implementation of BookService
    * TODO bind DefaultBookRepository as the implementation of BookRepository
    *
    * Take a look at:
    * `ratpack.guice.BindingsSpec#bind(publicType, implType)`
    */
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
