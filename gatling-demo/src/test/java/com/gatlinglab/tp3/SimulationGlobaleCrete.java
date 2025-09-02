package com.gatlinglab.tp3;

import com.gatlinglab.tp2.S01_GetProductsSimulation;
import com.gatlinglab.tp2.S02_GetProductByIDSimulation;
import com.gatlinglab.tp2.S03_GetProductsByCategorySimulation;
import com.gatlinglab.tp2.S04_GetProductsByPrefixSimulation;
import io.gatling.javaapi.core.Simulation;

import static com.gatlinglab.utils.HttpUtils.getHttpAssertions;
import static com.gatlinglab.utils.HttpUtils.getHttpProtocol;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;

public class SimulationGlobaleCrete extends Simulation {

    private static final float USER_RATE = Float.parseFloat(System.getProperty("user.rate", "20"));
    private static final float PIC_RATE = Float.parseFloat(System.getProperty("pic.user.rate", "200"));
    private static final int WARMUP_DURATION = Integer.parseInt(System.getProperty("warmup.duration", "10"));
    private static final int SCALING_DURATION = Integer.parseInt(System.getProperty("scaling.duration", "10"));
    private static final int DURATION = Integer.parseInt(System.getProperty("duration", "60"));

    {
        setUp(
                new S01_GetProductsSimulation().scn.injectOpen(
                        constantUsersPerSec(USER_RATE / 4).during(WARMUP_DURATION), // palier nominal
                        rampUsersPerSec(USER_RATE / 4).to(PIC_RATE / 4).during(SCALING_DURATION), // montée vers la crete
                        constantUsersPerSec(PIC_RATE / 4).during(DURATION)), // maintien en crete
                new S02_GetProductByIDSimulation().scn.injectOpen(
                        constantUsersPerSec(USER_RATE / 4).during(WARMUP_DURATION), // palier nominal
                        rampUsersPerSec(USER_RATE / 4).to(PIC_RATE / 4).during(SCALING_DURATION), // montée vers la crete
                        constantUsersPerSec(PIC_RATE / 4).during(DURATION)), // maintien en crete
                new S03_GetProductsByCategorySimulation().scn.injectOpen(
                        constantUsersPerSec(USER_RATE / 4).during(WARMUP_DURATION), // palier nominal
                        rampUsersPerSec(USER_RATE / 4).to(PIC_RATE / 4).during(SCALING_DURATION), // montée vers la crete
                        constantUsersPerSec(PIC_RATE / 4).during(DURATION)), // maintien en crete
                new S04_GetProductsByPrefixSimulation().scn.injectOpen(
                        constantUsersPerSec(USER_RATE / 4).during(WARMUP_DURATION), // palier nominal
                        rampUsersPerSec(USER_RATE / 4).to(PIC_RATE / 4).during(SCALING_DURATION), // montée vers la crete
                        constantUsersPerSec(PIC_RATE / 4).during(DURATION)) // maintien en crete
        )
                .protocols(getHttpProtocol())
                .assertions(getHttpAssertions());
    }
}
