package com.gatlinglab.tp2;

import io.gatling.javaapi.core.Simulation;

import static com.gatlinglab.utils.HttpUtils.getHttpAssertions;
import static com.gatlinglab.utils.HttpUtils.getHttpProtocol;
import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;

public class SimulationGlobale extends Simulation {

    {
        setUp(
                new S01_GetProductsSimulation().scn.injectOpen(atOnceUsers(1)),
                new S02_GetProductByIDSimulation().scn.injectOpen(atOnceUsers(1)),
                new S03_GetProductsByCategorySimulation().scn.injectOpen(atOnceUsers(1)),
                new S04_GetProductsByPrefixSimulation().scn.injectOpen(atOnceUsers(1))
        )
                .protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }
}
