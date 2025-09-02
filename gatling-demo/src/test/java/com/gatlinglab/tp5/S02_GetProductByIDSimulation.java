package com.gatlinglab.tp5;

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
                            .check(jsonPath("$.id").saveAs("retrievedId"))
                            .check(jsonPath("$.category").saveAs("category"))
            )
            // Exemple d'affichage des résultats
            .exec(session -> {
                int retrievedId = session.getInt("retrievedId");
                String category = session.getString("category");
                System.out.println("Produit récupéré : id=" + retrievedId + ", category=" + category);
                return session;
            })
            // Exemple de vérification
            .exec(session -> {
                int requestedId = session.getInt("id");
                int returnedId = session.getInt("retrievedId");
                if (requestedId != returnedId) {
                    throw new RuntimeException("ID retourné différent de l'ID demandé !");
                }
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
