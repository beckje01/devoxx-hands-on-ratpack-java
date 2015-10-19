import ratpack.groovy.handling.GroovyChain
import ratpack.groovy.handling.GroovyContext

class GroovyChainExtension {

  static GroovyChain soapAction(GroovyChain chain, String soapAction,
                @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler) throws IOException {

    chain.all(new SoapActionHandler(soapAction, handler))
  }
}
