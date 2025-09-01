package com.gatlinglab.tp1;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;

public class SimulationGlobale extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json");

    {
        setUp(
                new S01_GetProductsSimulation().scn.injectOpen(atOnceUsers(1)),
                new S02_GetProductByIDSimulation().scn.injectOpen(atOnceUsers(1)),
                new S03_GetProductsByCategorySimulation().scn.injectOpen(atOnceUsers(1)),
                new S04_GetProductsByPrefixSimulation().scn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}
