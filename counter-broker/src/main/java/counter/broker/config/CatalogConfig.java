package counter.broker.config;

import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Configuration
public class CatalogConfig {
    @Bean
    public Catalog catalog() {
        return new Catalog(singletonList(
                new ServiceDefinition(
                        "counter-service",
                        "Counter",
                        "Counter-as-a-Service",
                        true,
                        false,
                        singletonList(new Plan("ten", "ten", "Goes from 0 to 9", getPlanMetadata(), true)),
                        asList("counter/broker", "sample"),
                        getServiceDefinitionMetadata(),
                        null,
                        null)));
    }

    private Map<String, Object> getPlanMetadata() {
        Map<String, Object> planMetadata = new HashMap<>();
        planMetadata.put("bullets", asList("Your own counter", "Increment, decrement and reset", "Goes from 0 to 9"));
        return planMetadata;
    }

    private Map<String, Object> getServiceDefinitionMetadata() {
        Map<String, Object> sdMetadata = new HashMap<>();
        sdMetadata.put("displayName", "Counter");
        sdMetadata.put("longDescription", "Counter-as-a-Service");
        sdMetadata.put("providerDisplayName", "The Count");
        return sdMetadata;
    }
}
