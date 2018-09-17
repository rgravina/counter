package counter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@Service
class Counter {
    private Jedis jedis;

    void connect() {
        String vcapServices = System.getenv("VCAP_SERVICES");
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(vcapServices);
            JsonNode credentials = root.get("rediscloud").get(0).get("credentials");

            String hostname = credentials.get("hostname").textValue();
            String password = credentials.get("password").textValue();
            Integer port = credentials.get("port").asInt();

            jedis = new Jedis(hostname, port);
            jedis.auth(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void disconnect() {
        jedis.disconnect();
    }

    Integer increment(String key) {
        if (!jedis.exists(key)) {
            jedis.set(key, String.valueOf(0));
            return 0;
        } else {
            Integer value = Integer.valueOf(jedis.get(key)) + 1;
            jedis.set(key, String.valueOf(value));
            return value;
        }
    }
}
