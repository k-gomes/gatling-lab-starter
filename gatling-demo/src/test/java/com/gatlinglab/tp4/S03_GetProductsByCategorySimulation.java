package com.gatlinglab.tp4;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import static com.gatlinglab.utils.FeederUtils.buildCsvFeederCategory;
import static com.gatlinglab.utils.HttpUtils.getHttpAssertions;
import static com.gatlinglab.utils.HttpUtils.getHttpProtocol;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class S03_GetProductsByCategorySimulation extends Simulation {

    public ScenarioBuilder scn = scenario("Get products by category")
            .feed(buildCsvFeederCategory())
// DEBUG - Verifier le fonctionnement du feeder
//            .exec(
//                    session -> {
//                        System.out.println("CATEGORY = " + session.get("category"));
//                        return session;
//                    }
//            )
            .exec(
                    http("Get products by category")
                            .get("/products/search")
                            .queryParam("category", "#{category}")
                            .check(
                                    jsonPath("$[*].category").findAll().transform(categories ->
                                            categories.stream().distinct().count() == 1
                                    ).is(true)
                            )
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        )
                .protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }
}
