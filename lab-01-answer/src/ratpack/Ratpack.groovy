import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.handling.RequestLogger
import ratpack.path.PathBinding

import static ratpack.groovy.Groovy.ratpack

final Logger log = LoggerFactory.getLogger(Ratpack)

ratpack {
  handlers {
    all(RequestLogger.ncsa(log)) // log all requests

    prefix("user") {
      path("::f.*") { // starts with 'f' then 0 or more characters
        def binding = get(PathBinding)
        def username = binding.boundTo.indexOf('/') < 0 ?
          binding.boundTo :
          binding.boundTo.substring(0, binding.boundTo.indexOf('/'))

        log.info "Warning, request for $username"
        next()
      }

      prefix(":username") {
        get {
          response.send "user/${allPathTokens['username']}" // had to use `allPathTokens`
        }

        get("tweets") {
          response.send "user/${allPathTokens['username']}/tweets"
        }

        get("friends") {
          response.send "user/${allPathTokens['username']}/friends"
        }
      }

      all {
        byMethod {
          get {
            response.send "user"
          }
          post {
            response.send "user"
          }
        }
      }
    }

    prefix("api/ws") {
      when { request.headers.SOAPAction == "getTweets" } {
        all {
          response.send "${get(PathBinding).boundTo} - getTweets"
        }
      }

      when { request.headers.SOAPAction == "getFriends" } {
        all {
          response.send "${get(PathBinding).boundTo} - getFriends"
        }
      }
    }

    prefix("assets") {
      files { dir "public" }
    }

    files {
      dir "pages" indexFiles "index.html"
    }

    all {
      response.send "Hello Devoxx!"
    }
  }
}
