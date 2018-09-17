package counter.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

@Configuration
public class RedisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        String vcapServices = System.getenv("VCAP_SERVICES");
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(vcapServices);
            JsonNode credentials = root.get("rediscloud").get(0).get("credentials");

            String hostname = credentials.get("hostname").textValue();
            String password = credentials.get("password").textValue();
            Integer port = credentials.get("port").asInt();

            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
            jedisConnectionFactory.setHostName(hostname);
            jedisConnectionFactory.setPort(port);
            jedisConnectionFactory.setPassword(password);

            return jedisConnectionFactory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
