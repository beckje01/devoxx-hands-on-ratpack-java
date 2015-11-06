package lab05;

import ratpack.guice.Guice;
import ratpack.handlebars.HandlebarsModule;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

import static ratpack.handlebars.Template.handlebarsTemplate;

public class Lab05 {
  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
      .serverConfig(s -> s.baseDir(BaseDir.find()))
      .registry(Guice.registry(bindingsSpec -> bindingsSpec
        .bind(BookRepository.class, DefaultBookRepository.class)
        .bind(BookService.class, DefaultBookService.class)
        .bind(BookRenderer.class)
        .module(HandlebarsModule.class)
      ))
      .handlers(chain -> chain
        .get(ctx -> ctx.render("Hello Devoxx!"))
        .get("welcome", ctx ->
          ctx.render(
            handlebarsTemplate("index", modelBuilder -> modelBuilder.put("welcomeMessage", "Hello Devoxx!"))
          )
        )
        .get("api/book/:isbn", ctx -> {
          BookService bookService = ctx.get(BookService.class);
          String isbn = ctx.getPathTokens().get("isbn");
          ctx.render(bookService.getBook(isbn));
        })
      )
    );
  }
}
