package lab06;

import ratpack.guice.Guice;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class Lab06 {

  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec -> {
        ratpackServerSpec
          .serverConfig(s -> s.baseDir(BaseDir.find()))
          .registry(Guice.registry(bindingsSpec -> {
            //Add any needed modules
          }))
          .handlers(chain -> {
            //Add handlers
          });
      }

    );
  }

}
