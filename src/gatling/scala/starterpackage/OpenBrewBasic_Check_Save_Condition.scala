package starterpackage

//import
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class OpenBrewBasic_Check_Save_Condition extends Simulation {
  //Create http protocol configuration
  val httpProtocol=http.baseUrl("https://api.openbrewerydb.org")
  val csvFeeder=csv("data/brewery_csv.csv").eager.circular

  //Create Scenario which will send http request

  val scn = scenario("find_breweries")
    .feed(csvFeeder)
    .exec(http("all_breweries").get("/breweries").check(status.is(200),
      substring("Oklahoma").exists,bodyString.saveAs("ResponseBody"))).exitHereIfFailed
    .exec { session =>
      print(session("ResponseBody").as[String])
      session
    }
    .pause(5 seconds)
    .doIfOrElse( session => session("ResponseBody").as[String].contains("(512) Brewing Cojf")) {
      exec(http("get_single_brewery").get("/breweries/${brewery_id}").check(status.is(200)
        , responseTimeInMillis.lte(1600), bodyString.saveAs("ResponseBody")))
    }
    {
    exec { session =>
    println("Condition not met")
    session
  }
  }

  //Inject Load
  setUp(scn.inject(atOnceUsers(2))).protocols(httpProtocol)

}
