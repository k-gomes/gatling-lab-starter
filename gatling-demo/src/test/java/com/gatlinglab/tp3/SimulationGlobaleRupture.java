package com.gatlinglab.tp3;

import com.gatlinglab.tp2.S01_GetProductsSimulation;
import com.gatlinglab.tp2.S02_GetProductByIDSimulation;
import com.gatlinglab.tp2.S03_GetProductsByCategorySimulation;
import com.gatlinglab.tp2.S04_GetProductsByPrefixSimulation;
import io.gatling.javaapi.core.Simulation;

import static com.gatlinglab.utils.HttpUtils.getHttpAssertions;
import static com.gatlinglab.utils.HttpUtils.getHttpProtocol;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;

public class SimulationGlobaleRupture extends Simulation {

    private static final float USER_RATE = Float.parseFloat(System.getProperty("user.rate", "20"));
    private static final float RUPTURE_RATE = Float.parseFloat(System.getProperty("rupture.user.rate", "2000"));
    private static final int SCALING_DURATION = Integer.parseInt(System.getProperty("scaling.duration", "60"));

    {
        setUp(
                new S01_GetProductsSimulation().scn.injectOpen(
                        rampUsersPerSec(USER_RATE / 4).to(RUPTURE_RATE / 4).during(SCALING_DURATION)),
                new S02_GetProductByIDSimulation().scn.injectOpen(
                        rampUsersPerSec(USER_RATE / 4).to(RUPTURE_RATE / 4).during(SCALING_DURATION)),
                new S03_GetProductsByCategorySimulation().scn.injectOpen(
                        rampUsersPerSec(USER_RATE / 4).to(RUPTURE_RATE / 4).during(SCALING_DURATION)),
                new S04_GetProductsByPrefixSimulation().scn.injectOpen(
                        rampUsersPerSec(USER_RATE / 4).to(RUPTURE_RATE / 4).during(SCALING_DURATION))
        )
                .protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }
}
