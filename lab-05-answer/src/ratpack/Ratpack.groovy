import ratpack.groovy.template.MarkupTemplateModule

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bind BookRepository, DefaultBookRepository
    bind BookService, DefaultBookService
    bind BookRenderer
    module MarkupTemplateModule
  }

  handlers {
    get {
      render "Hello Devoxx!"
    }

    get("welcome") {
      render groovyMarkupTemplate("index.gtpl", welcomeMessage: "Hello Devoxx!")
    }

    get("api/book/:isbn") { BookService bookService ->
      String isbn = pathTokens["isbn"]
      render bookService.getBook(isbn)
    }
  }
}
