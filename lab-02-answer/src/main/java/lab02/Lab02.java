package lab02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.RequestLogger;
import ratpack.path.PathBinding;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class Lab02 {
  final static Logger log = LoggerFactory.getLogger(Lab02.class);

  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec ->
        ratpackServerSpec
          .serverConfig(s -> s.baseDir(BaseDir.find()))
          .handlers(chain ->
              chain
                .all(RequestLogger.ncsa(log))
                .prefix("user", new UserEndpoint())
                .prefix("api/ws", apiChain -> apiChain
                    // if the prefix is removed, the tests still pass.  Must be careful that handlers further up the chain don't also match
                    .all(new SoapActionHandler("getFriends", ctx -> {
                      String binding = ctx.get(PathBinding.class).getBoundTo();
                      ctx.getResponse().send(binding + " - getFriends");
                    }))
                    // Here we have a static factory method that's making use of `ratpack.handling.Handlers.when`
                    .all(MyHandlers.soapAction("getTweets", ctx -> {
                      String binding = ctx.get(PathBinding.class).getBoundTo();
                      ctx.getResponse().send(binding + " - getTweets");
                    }))
                    .all(MyHandlers.soapAction("getFavourites", ctx -> {
                      String binding = ctx.get(PathBinding.class).getBoundTo();
                      ctx.getResponse().send(binding + " - getFavourites");
                    }))
                )
                .prefix("assets", assetsChain -> assetsChain
                    .files(fileHandlerSpec -> fileHandlerSpec
                        .dir("public")
                    )
                )
                .files(fileHandlerSpec -> fileHandlerSpec
                    .dir("pages").indexFiles("index.html")
                )
                .all(context ->
                    context.getResponse().send("Hello Devoxx!")
                )
          )
    );
  }
}
