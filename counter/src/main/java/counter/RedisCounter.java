package counter;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
class RedisCounter implements Counter {
    private RedisTemplate<String, Object> redisTemplate;

    public RedisCounter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Integer increment(String key) {
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, String.valueOf(0));
            return 0;
        } else {
            Integer value = Integer.valueOf((String)redisTemplate.opsForValue().get(key)) + 1;
            redisTemplate.opsForValue().set(key, String.valueOf(value));
            return value;
        }
    }
}
