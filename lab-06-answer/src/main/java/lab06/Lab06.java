package lab06;

import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.pac4j.http.profile.HttpProfile;
import ratpack.guice.Guice;
import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.pac4j.RatpackPac4j;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.session.SessionModule;

public class Lab06 {

  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec ->
      ratpackServerSpec
        .serverConfig(s -> s.baseDir(BaseDir.find()))
        .registry(Guice.registry(bindingsSpec -> bindingsSpec.module(SessionModule.class)))
        .handlers(chain ->
          chain
            .all(RatpackPac4j.authenticator(
              new DirectBasicAuthClient(new SimpleTestUsernamePasswordAuthenticator()))
            )
            .get(ctx -> ctx.render("Root GET"))
            .all(RatpackPac4j.requireAuth(DirectBasicAuthClient.class))
            .get("secure", new InjectionHandler() {
              void handle(Context ctx, HttpProfile profile) {
                ctx.render("Secure stuff for " + profile.getUsername() + "!");
              }
            })
        )
    );
  }

}
