package com.gatlinglab.tp5;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import java.util.List;

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
                            // Sauvegarde tous les id dans la session sous la variable "productIds"
                            .check(jsonPath("$[*].id").findAll().saveAs("productIds"))
            )
            // Log des id dans la console
            .exec(session -> {
                List<Integer> ids = session.getList("productIds");
                System.out.println("Product IDs: " + ids);
                return session;
            })
            // DEBUG SESSION
            .exec(session -> {
                System.out.println(session);
                return session;
            });

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        )
                .protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }
}
