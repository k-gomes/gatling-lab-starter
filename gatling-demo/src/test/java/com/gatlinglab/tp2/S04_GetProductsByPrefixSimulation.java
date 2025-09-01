package com.gatlinglab.tp2;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import static com.gatlinglab.tp2.utils.HttpUtils.getHttpAssertions;
import static com.gatlinglab.tp2.utils.HttpUtils.getHttpProtocol;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class S04_GetProductsByPrefixSimulation extends Simulation {

    ScenarioBuilder scn = scenario("Get products by prefix")
            .exec(
                    http("Get products by prefix")
                            .get("/products/search-by-name")
                            .queryParam("prefix", "Product 1")
                            .queryParam("size", 9)
                            .check(
                                    jsonPath("$[*].name").findAll().transform(names ->
                                            names.stream().allMatch(name -> name.startsWith("Product 1"))
                                    ).is(true)
                            )
                            .check(jsonPath("$[*].name").count().is(9)) // liste de "size" produits
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        )
                .protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }
}
