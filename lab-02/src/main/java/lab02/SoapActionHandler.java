package lab02;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class SoapActionHandler implements Handler {

  private final String soapAction;
  private final Handler handler;

  public SoapActionHandler(String soapAction, Handler handler) {
    this.soapAction = soapAction;
    this.handler = handler;
  }

  @Override
  public void handle(Context ctx) throws Exception {
    // TODO handle soapAction if matches incoming soapAction
    ctx.next();
  }
}
