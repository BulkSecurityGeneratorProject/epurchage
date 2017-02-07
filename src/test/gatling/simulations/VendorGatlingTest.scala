import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Vendor entity.
 */
class VendorGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the Vendor entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJSON
        .check(header.get("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all vendors")
            .get("/api/vendors")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new vendor")
            .post("/api/vendors")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "regNo":"SAMPLE_TEXT", "companyName":"SAMPLE_TEXT", "contactPerson":"SAMPLE_TEXT", "address":"SAMPLE_TEXT", "productType":"SAMPLE_TEXT", "email":"SAMPLE_TEXT", "panNumber":"SAMPLE_TEXT", "tinNumber":"SAMPLE_TEXT", "stNumber":"SAMPLE_TEXT", "exerciseNumber":"SAMPLE_TEXT", "pfNumber":"SAMPLE_TEXT", "esicNumber":"SAMPLE_TEXT", "accountName":"SAMPLE_TEXT", "accountNumber":null, "ifscCode":"SAMPLE_TEXT", "swiftNumber":"SAMPLE_TEXT", "bankBranch":"SAMPLE_TEXT", "vendorType":"SAMPLE_TEXT", "vendorstatus":"SAMPLE_TEXT", "vendorTimeSpan":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_vendor_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created vendor")
                .get("${new_vendor_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created vendor")
            .delete("${new_vendor_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
