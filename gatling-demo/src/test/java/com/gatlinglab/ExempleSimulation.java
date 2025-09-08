package com.gatlinglab;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class ExempleSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://HOST:PORT")
            .acceptHeader("application/json");

    ScenarioBuilder scn = scenario("SCENARIO NAME")
            .exec(
                    http("REQUEST NAME")
                            .get("/ENDPOINT")
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}
