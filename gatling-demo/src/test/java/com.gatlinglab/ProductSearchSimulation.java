package com.gatlinglab;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class ProductSearchSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json");

    ScenarioBuilder scn = scenario("Search Products")
            .exec(
                    http("Search by name")
                            .get("/products/search-by-name")
                            .queryParam("prefix", "Product 1")
                            .queryParam("size", "50")
                            .check(status().is(200))
            );

    {
        setUp(
                scn.injectOpen(
                        atOnceUsers(5),
                        rampUsers(20).during(java.time.Duration.ofSeconds(10))
                )
        ).protocols(httpProtocol);
    }
}
