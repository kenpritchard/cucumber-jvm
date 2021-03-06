package io.cucumber.core.options;

import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.core.order.PickleOrder;
import io.cucumber.core.order.StandardPickleOrders;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class PickleOrderParser {
    private static final Logger log = LoggerFactory.getLogger(RuntimeOptionsParser.class);

    private static final Pattern RANDOM_AND_SEED_PATTERN = Pattern.compile("random(?::(\\d+))?");

    static PickleOrder parse(String argument) {

        if ("reverse".equals(argument)) {
            return StandardPickleOrders.reverseLexicalUriOrder();
        }

        Matcher matcher = RANDOM_AND_SEED_PATTERN.matcher(argument);
        if (matcher.matches()) {
            long seed = Math.abs(new Random().nextLong());
            String seedString = matcher.group(1);
            if (seedString != null) {
                seed = Long.parseLong(seedString);
            } else {
                log.info("Using random scenario order. Seed: " + seed);
            }

            return StandardPickleOrders.random(seed);
        }

        throw new IllegalArgumentException("Invalid order. Must be either reverse, random or random:<long>");
    }
}
