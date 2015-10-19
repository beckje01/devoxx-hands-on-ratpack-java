import ratpack.groovy.Groovy
import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler
import ratpack.handling.Handler

class SoapActionHandler extends GroovyHandler {

    String soapAction
    Handler handler

    SoapActionHandler(String soapAction,
                      @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler) {
        this.soapAction = soapAction
        this.handler = Groovy.groovyHandler(handler)
    }

    @Override
    protected void handle(GroovyContext context) {
        context.next()
    }
}
