package com.gatlinglab.tp4;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import static com.gatlinglab.utils.FeederUtils.buildJsonFeederPrefixes;
import static com.gatlinglab.utils.FeederUtils.buildRandomFeederProperty;
import static com.gatlinglab.utils.HttpUtils.getHttpAssertions;
import static com.gatlinglab.utils.HttpUtils.getHttpProtocol;
import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class S04_GetProductsByPrefixSimulation_Better extends Simulation {

    public ScenarioBuilder scn = scenario("Get products by prefix")
            .feed(buildJsonFeederPrefixes())
            .feed(buildRandomFeederProperty("size", 1000))
// DEBUG - Verifier le fonctionnement du feeder
//            .exec(
//                    session -> {
//                        System.out.println("PREFIX = " + session.get("prefix"));
//                        System.out.println("SIZE = " + session.get("size"));
//                        return session;
//                    }
//            )
            .exec(
                    http("Get products by prefix")
                            .get("/products/search-by-name")
                            .queryParam("prefix", "#{prefix}")
                            .queryParam("size", "#{size}")
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        )
                .protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }
}
