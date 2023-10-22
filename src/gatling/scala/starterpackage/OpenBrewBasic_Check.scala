package starterpackage

//import
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class OpenBrewBasic_Check extends Simulation {
  //Create http protocol configuration
  val httpProtocol  =http.baseUrl("https://api.openbrewerydb.org")

  //Create Scenario which will send http request

  val scn = scenario("find_breweries")
    .exec(http("all_breweries").get("/breweries").check(status.is(200),
      substring("Oklahoma").exists))
    .pause(5 seconds)
    .exec(http("get_single_brewery").get("/breweries/9c5a66c8-cc13-416f-a5d9-0a769c87d318\"").check(status.is(200)
      ,responseTimeInMillis.lte(1600)))

  //Inject Load
  setUp(scn.inject(atOnceUsers(2))).protocols(httpProtocol)

}
