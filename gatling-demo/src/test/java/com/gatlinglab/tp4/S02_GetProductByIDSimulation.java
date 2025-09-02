package com.gatlinglab.tp4;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import static com.gatlinglab.utils.FeederUtils.buildRandomFeederId;
import static com.gatlinglab.utils.HttpUtils.getHttpAssertions;
import static com.gatlinglab.utils.HttpUtils.getHttpProtocol;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class S02_GetProductByIDSimulation extends Simulation {

    public ScenarioBuilder scn = scenario("Get product by id")
            .feed(buildRandomFeederId(50000))
// DEBUG - Verifier le fonctionnement du feeder
//            .exec(
//                    session -> {
//                        System.out.println("ID = " + session.get("id"));
//                        return session;
//                    }
//            )
            .exec(
                    http("Get product by id")
                            .get("/products/#{id}")
                            .check(jsonPath("$.id").count().is(1))
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        )
                .protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }
}
