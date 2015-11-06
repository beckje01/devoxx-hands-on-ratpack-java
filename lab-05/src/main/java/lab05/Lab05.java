package lab05;

import ratpack.guice.Guice;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class Lab05 {
  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
      .serverConfig(s -> s.baseDir(BaseDir.find()))
      .registry(Guice.registry(bindingsSpec -> bindingsSpec
          .bind(BookRepository.class, DefaultBookRepository.class)
          .bind(BookService.class, DefaultBookService.class)
      ))
      .handlers(chain -> {
        // TODO implement me
      })
    );
  }
}
