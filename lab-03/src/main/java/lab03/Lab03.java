package lab03;

import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.server.RatpackServer;

public class Lab03 {
  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
      .handlers(chain -> chain
        /*
        * TODO register DefaultBookService as the implementation of BookService in the registry
        *
        * Take a look at:
        * `ratpack.handling.Chain#registry(registry)`
        * `ratpack.registry.Registry#single(object)`
        */
        .prefix("book/:isbn", bookChain ->
          /*
          * TODO add your new common handler here
          *
          * Remember to make the result available in the context
          *
          * Take a look at:
          * `ratpack.handling.Context#next(registry)`
          */
          bookChain
            .get("title", ctx -> {
              // Objects added to the registry by upstream handlers are available via type-lookup
              BookService bookService = ctx.get(BookService.class);

              //TODO refactor this into a common handler for this chain
              String isbn = ctx.getAllPathTokens().get("isbn");
              Book book = bookService.getBook(isbn);

              ctx.getResponse().send(book.title);
            })
            .get("author", new InjectionHandler() {
              void handle(Context ctx, BookService bookService) { // Registry objects can also be "injected" into InjectionHandlers
                  //TODO refactor this into a common handler for this chain
                  String isbn = ctx.getAllPathTokens().get("isbn");

                  Book book = bookService.getBook(isbn);
                  ctx.getResponse().send(book.author);
              }
            })
        )
      ));
  }
}
