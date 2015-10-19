import ratpack.handling.RequestLogger
import ratpack.path.PathBinding

import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    all(RequestLogger.ncsa()) // log all requests

    prefix("user", new UserEndpoint())

    prefix("api/ws") {
      // if the prefix is removed, the tests still pass.  Must be careful that handlers further up the chain don't also match
      all(new SoapActionHandler("getFriends", {
        response.send "${get(PathBinding).boundTo} - getFriends"
      }))

      // Here we have a static factory method that's making use of `ratpack.handling.Handlers.when`
      all(MyHandlers.soapAction("getTweets", {
        response.send "${get(PathBinding).boundTo} - getTweets"
      }))

      // Here we are using a Groovy extension module to add `soapAction` to `GroovyChain`
      soapAction("getFavourites") {
        response.send "${get(PathBinding).boundTo} - getFavourites"
      }
    }

    prefix("assets") {
      files { dir "public" }
    }

    files { dir "pages" indexFiles "index.html" }

    all {
      response.send "Hello Devoxx!"
    }

  }
}
