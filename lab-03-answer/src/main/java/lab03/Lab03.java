package lab03;

import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.registry.Registry;
import ratpack.server.RatpackServer;

public class Lab03 {
  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
      .registry(Registry.single(BookService.class, new DefaultBookService()))
      .handlers(chain -> chain
        .prefix("book/:isbn", bookChain ->
            bookChain
              .all(new InjectionHandler() {
                void handle(Context ctx, BookService bookService) {
                  String isbn = ctx.getAllPathTokens().get("isbn");
                  Book book = bookService.getBook(isbn);

                  ctx.next(Registry.single(book));
                }
              })
              .get("title", ctx ->
                ctx.getResponse().send(ctx.get(Book.class).title)
              )
              .get("author", new InjectionHandler() {
                void handle(Context ctx, Book book) {
                  ctx.getResponse().send(book.author);
                }
              })
        )
      ));
  }
}
