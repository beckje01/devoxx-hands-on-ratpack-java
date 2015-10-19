import ratpack.groovy.Groovy
import ratpack.groovy.handling.GroovyContext
import ratpack.handling.Handler
import ratpack.handling.Handlers

class MyHandlers {

  public static Handler soapAction(String soapAction,
                     @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler) {
    return Handlers.when(
      { context -> context.request.headers.SOAPAction == soapAction },
      Groovy.groovyHandler(handler))
  }

}
