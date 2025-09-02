package com.gatlinglab.tp2;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import static com.gatlinglab.utils.HttpUtils.getHttpAssertions;
import static com.gatlinglab.utils.HttpUtils.getHttpProtocol;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class S01_GetProductsSimulation extends Simulation {

    public ScenarioBuilder scn = scenario("Get products")
            .exec(
                    http("Get products")
                            .get("/products")
                            .check(jsonPath("$[*].id").count().is(20)) // liste de 20 produits
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        )
                .protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }
}
