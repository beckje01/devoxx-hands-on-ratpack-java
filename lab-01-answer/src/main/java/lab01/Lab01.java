package lab01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.RequestLogger;
import ratpack.http.Headers;
import ratpack.path.PathBinding;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class Lab01 {

  final static Logger log = LoggerFactory.getLogger(Lab01.class);

  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec ->
        ratpackServerSpec
          .serverConfig(s -> s.baseDir(BaseDir.find()))
          .handlers(chain ->
              chain
                .all(RequestLogger.ncsa(log))
                .prefix("user", userChain -> userChain
                    .path("::f.*", context -> {
                      PathBinding binding = context.get(PathBinding.class);
                      String username = binding.getBoundTo().indexOf("/") < 0 ?
                        binding.getBoundTo() :
                        binding.getBoundTo().substring(0, binding.getBoundTo().indexOf("/"));

                      log.info("Warning, request for {}", username);
                      context.next();
                    })
                    .prefix(":username", usernameChain ->
                        usernameChain
                          .get(context -> {
                            String username = context.getAllPathTokens().get("username");
                            context.getResponse().send("user/" + username);
                          })
                          .get("tweets", context -> {
                            String username = context.getAllPathTokens().get("username");
                            context.getResponse().send("user/" + username + "/tweets");
                          })
                          .get("friends", context -> {
                            String username = context.getAllPathTokens().get("username");
                            context.getResponse().send("user/" + username + "/friends");
                          })
                    )
                    .all(context -> context
                        .byMethod(byMethodSpec -> byMethodSpec
                            .get(() -> context.getResponse().send("user"))
                            .post(() -> context.getResponse().send("user"))
                        )
                    )
                )
                .prefix("api/ws", apiChain -> apiChain
                    .when(ctx -> {
                        Headers headers = ctx.getRequest().getHeaders();
                        return headers.contains("SOAPAction") &&
                          headers.get("SOAPAction").equals("getTweets");
                      }, whenChain -> whenChain
                        .all(ctx -> {
                          String bound = ctx.get(PathBinding.class).getBoundTo();
                          ctx.getResponse().send(bound + " - getTweets");
                        })
                    ).when(ctx -> {
                        Headers headers = ctx.getRequest().getHeaders();
                        return headers.contains("SOAPAction") &&
                          headers.get("SOAPAction").equals("getFriends");
                      }, whenChain -> whenChain
                        .all(ctx -> {
                          String bound = ctx.get(PathBinding.class).getBoundTo();
                          ctx.getResponse().send(bound + " - getFriends");
                        })
                    )
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
