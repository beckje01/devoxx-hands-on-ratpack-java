package lab07;

import com.google.common.collect.Maps;
import ratpack.guice.Guice;
import ratpack.h2.H2Module;
import ratpack.hikari.HikariModule;
import ratpack.jackson.Jackson;
import ratpack.server.RatpackServer;

import java.util.Map;

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
            bookService
              .getBook(isbn)
              .onNull(() -> ctx.clientError(404))
              .map(Jackson::json)
              .then(ctx::render);
          })
          .all(ctx -> {
            BookService bookService = ctx.get(BookService.class);
            ctx.byMethod(byMethodSpec -> byMethodSpec
              .get(() ->
                // Render the promised list of books as json
                // Hint - Checkout ratpack.exec.Promise.then(Action)
                // Hint - Use Ratpack's Jackson integration to render the Promised json
                bookService.getBooks().map(Jackson::json).then(ctx::render)
              )
              .post(() ->
                // Parse incoming json, store book, then return {"message":"success"}
                // Hint - Checkout ratpack.handling.Context#parse(Class)
                ctx.parse(Book.class)
                  .flatMap(book -> bookService
                    .addBook(book)
                    .map(() -> {
                      Map<String, String> result = Maps.newHashMap();
                      result.put("message", "success");
                      return result;
                    }))
                  .map(Jackson::json)
                  .then(ctx::render)
              )
            );
          })
        )
      )
    );
  }
}
