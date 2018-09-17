package counter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
public class CounterController {
    @GetMapping("/")
    public Integer currentValue(RestTemplate restTemplate) {
        String vcapServices = System.getenv("VCAP_SERVICES");
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(vcapServices);
            JsonNode credentials = root.get("Counter").get(0).get("credentials");
            String url = credentials.get("url").textValue();
            String key = credentials.get("key").textValue();

            return restTemplate.getForObject
                    (UriComponentsBuilder
                            .fromUriString(url)
                            .queryParam("key", key)
                            .toUriString(), Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
