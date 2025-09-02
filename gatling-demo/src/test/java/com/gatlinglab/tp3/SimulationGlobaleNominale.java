package com.gatlinglab.tp3;

import com.gatlinglab.tp2.S01_GetProductsSimulation;
import com.gatlinglab.tp2.S02_GetProductByIDSimulation;
import com.gatlinglab.tp2.S03_GetProductsByCategorySimulation;
import com.gatlinglab.tp2.S04_GetProductsByPrefixSimulation;
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
                new S04_GetProductsByPrefixSimulation().scn.injectOpen(constantUsersPerSec(USER_RATE / 4).during(DURATION))
        )
                .protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }
}
