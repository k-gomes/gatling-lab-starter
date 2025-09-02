package com.gatlinglab.tp4;

import io.gatling.javaapi.core.Simulation;

import static com.gatlinglab.utils.HttpUtils.getHttpAssertions;
import static com.gatlinglab.utils.HttpUtils.getHttpProtocol;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;

public class SimulationGlobaleNominale extends Simulation {

    private static final float USER_RATE = Float.parseFloat(System.getProperty("user.rate", "20"));
    private static final int DURATION = Integer.parseInt(System.getProperty("duration", "60"));

    {
        setUp(
                new S01_GetProductsSimulation().scn.injectOpen(constantUsersPerSec(USER_RATE / 4).during(DURATION)),
                new S02_GetProductByIDSimulation().scn.injectOpen(constantUsersPerSec(USER_RATE / 4).during(DURATION)),
                new S03_GetProductsByCategorySimulation().scn.injectOpen(constantUsersPerSec(USER_RATE / 4).during(DURATION)),
                new S04_GetProductsByPrefixSimulation_Better().scn.injectOpen(constantUsersPerSec(USER_RATE / 4).during(DURATION))
        )
                .protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }
}
