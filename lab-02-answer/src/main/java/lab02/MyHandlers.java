package lab02;

import ratpack.handling.Handler;
import ratpack.handling.Handlers;

interface MyHandlers {
  static Handler soapAction(String soapAction, Handler handler) {
    return Handlers
      .when(
        ctx -> ctx
          .getRequest()
          .getHeaders()
          .get("SOAPAction").equals(soapAction),
        handler::handle);
  }
}
