package counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {
    @Autowired
    public Counter counter;

    @GetMapping("/")
    public Integer currentValue(@RequestParam String key) {
        return counter.increment(key);
    }
}
