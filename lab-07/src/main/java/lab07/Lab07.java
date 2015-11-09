package lab07;

import ratpack.exec.Promise;
import ratpack.guice.Guice;
import ratpack.h2.H2Module;
import ratpack.hikari.HikariModule;
import ratpack.server.RatpackServer;

import java.util.List;

public class Lab07 {
  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
      .registry(Guice.registry(bindingsSpec -> bindingsSpec
        .module(new H2Module("sa", "", "jdbc:h2:mem:lab07;MODE=MySQL"))
        .module(HikariModule.class, hikariConfig -> {
          hikariConfig.addDataSourceProperty("user", "sa");
          hikariConfig.addDataSourceProperty("password", "");
          hikariConfig.addDataSourceProperty("URL", "jdbc:h2:mem:lab07;INIT=RUNSCRIPT FROM 'classpath:init.sql'");
          hikariConfig.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        })
        .bind(BookRepository.class, DefaultBookRepository.class)
        .bind(BookService.class, DefaultBookService.class)
      ))
      .handlers(chain -> chain
        .prefix("api/book", apiChain -> apiChain
          .get(":isbn", ctx -> {
            BookService bookService = ctx.get(BookService.class);
            String isbn = ctx.getPathTokens().get("isbn");

            // Hint - Checkout ratpack.exec.Promise.then(Action)
            // Hint - Use Ratpack's Jackson integration to render the Promised json
            // If book doesn't exist return 404
            Promise<Book> book = bookService.getBook(isbn);
            // TODO implement me
          })
          .all(ctx -> {
            BookService bookService = ctx.get(BookService.class);
            ctx.byMethod(byMethodSpec -> byMethodSpec
              .get(() -> {
                // Render the promised list of books as json
                // Hint - Checkout ratpack.exec.Promise.then(Action)
                // Hint - Use Ratpack's Jackson integration to render the Promised json
                Promise<List<Book>> books = bookService.getBooks();
                // TODO implement me
              })
              .post(() -> {
                // Parse incoming json, store book, then return {"message":"success"}
                // Hint - Checkout ratpack.handling.Context#parse(Class)
                // TODO implement me
              })
            );
          })
        )
      )
    );
  }
}
