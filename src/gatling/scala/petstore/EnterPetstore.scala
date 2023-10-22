package petstore

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class EnterPetstore extends Simulation {

  private val httpProtocol = http
    .baseUrl("https://petstore.octoperf.com")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.9,ar;q=0.8")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36")
  
  private val headers_0 = Map(
  		"sec-ch-ua" -> """Chromium";v="118", "Google Chrome";v="118", "Not=A?Brand";v="99""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows",
  		"sec-fetch-dest" -> "document",
  		"sec-fetch-mode" -> "navigate",
  		"sec-fetch-site" -> "none",
  		"sec-fetch-user" -> "?1"
  )
  
  private val headers_1 = Map(
  		"sec-ch-ua" -> """Chromium";v="118", "Google Chrome";v="118", "Not=A?Brand";v="99""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows",
  		"sec-fetch-dest" -> "document",
  		"sec-fetch-mode" -> "navigate",
  		"sec-fetch-site" -> "same-origin",
  		"sec-fetch-user" -> "?1"
  )


  private val scn = scenario("EnterPetstore")
    .exec(
      http("request_0")
        .get("/")
        .headers(headers_0)
    )
    .pause(3)
    .exec(
      http("request_1")
        .get("/actions/Catalog.action")
        .headers(headers_1)
    )

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
