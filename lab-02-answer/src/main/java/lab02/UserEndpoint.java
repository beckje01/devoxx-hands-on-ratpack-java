package lab02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.path.PathBinding;

public class UserEndpoint implements Action<Chain> {

  final static Logger log = LoggerFactory.getLogger(UserEndpoint.class);

  @Override
  public void execute(Chain userChain) throws Exception {
    userChain
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
      );
  }
}
