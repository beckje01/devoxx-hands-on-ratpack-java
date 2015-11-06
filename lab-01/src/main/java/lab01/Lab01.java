package lab01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class Lab01 {

  final static Logger log = LoggerFactory.getLogger(Lab01.class);

  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec ->
        ratpackServerSpec
          .serverConfig(s -> s.baseDir(BaseDir.find()))
          .handlers(chain -> {
            // TODO Implement chain
          })
    );
  }
}
