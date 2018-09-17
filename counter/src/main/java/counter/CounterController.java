package counter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {
    @GetMapping("/")
    public Integer currentValue(
            @RequestParam String key,
            Counter counter) {
        counter.connect();
        Integer value = counter.increment(key);
        counter.disconnect();
        return value;
    }
}
