package com.gatlinglab.tp2.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.gatling.javaapi.core.Assertion;
import io.gatling.javaapi.core.ProtocolBuilder;

import java.util.Arrays;
import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.http.HttpDsl.http;

public class HttpUtils {

    public static ProtocolBuilder getHttpProtocol() {

        Config config = ConfigFactory.load("configuration.properties");

        return http
                .baseUrl(config.getString("http.url"))
                .acceptHeader("application/json");
    }

    public static List<Assertion> getHttpAssertions() {
        return Arrays.asList(
                global().failedRequests().percent().is(0D),
                global().responseTime().max().lte(1000)
        );
    }


}
