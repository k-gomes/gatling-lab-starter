package com.gatlinglab.tp6;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import java.util.List;

import static com.gatlinglab.utils.HttpUtils.getHttpAssertions;
import static com.gatlinglab.utils.HttpUtils.getHttpProtocol;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class SimulationParcoursUtilisateur extends Simulation {

    ScenarioBuilder scn = scenario("Parcours utilisateur sur api-demo")

            // Liste de 20 produits
            .exec(
                    http("Get all products")
                            .get("/products")
                            .check(jsonPath("$[*].id").findAll().saveAs("productIds"))
                            .check(jsonPath("$[*].category").findAll().saveAs("productCategories"))
                            .check(jsonPath("$[*].name").findAll().saveAs("productNames"))
            )
            .pause(2, 5) // pause aléatoire entre 2 et 5 secondes

            // Sélection aléatoire d’un produit dans la liste
            .exec(session -> {
                List<Integer> ids = session.getList("productIds");
                List<String> categories = session.getList("productCategories");
                List<String> names = session.getList("productNames");

                int index = (int) (Math.random() * ids.size());
                session = session.set("selectedId", ids.get(index));
                session = session.set("selectedCategory", categories.get(index));
                session = session.set("selectedNamePrefix", names.get(index).substring(0,3));
                return session;
            })
            .pause(1, 3)

            // Détails du produit sélectionné
            .exec(
                    http("Get product by id")
                            .get("/products/#{selectedId}")
                            .check(jsonPath("$.id").count().is(1))
            )
            .pause(1, 3)

            // Recherche par catégorie
            .exec(
                    http("Search products by category")
                            .get("/products/search")
                            .queryParam("category", "#{selectedCategory}")
                            .check(
                                    jsonPath("$[*].category").findAll().transform(categories ->
                                            categories.stream().distinct().count() == 1
                                    ).is(true)
                            )
            )
            .pause(2, 4)

            // Recherche par préfixe
            .exec(
                    http("Search products by name prefix")
                            .get("/products/search-by-name")
                            .queryParam("prefix", "#{selectedNamePrefix}")
                            .queryParam("size", 5)
            )
            .pause(3, 6);

    {
        // Injection en modèle fermé : 5 utilisateurs simultanés exécutant le parcours
        setUp(
                scn.injectClosed(
                        constantConcurrentUsers(500).during(60) // 5 utilisateurs pendant 60s
                )
        ).protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }

}
