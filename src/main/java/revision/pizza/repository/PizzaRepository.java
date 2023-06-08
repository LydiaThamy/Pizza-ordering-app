package revision.pizza.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;

@Repository
public class PizzaRepository {
    
    @Autowired
    @Qualifier("pizza")
    private RedisTemplate<String, Object> template;

    public void persistOrder(JsonObject json, String id) {
        template.opsForValue().set(id, json.toString());
    }

    public String getOrder(String id) {
        return (String) template.opsForValue().get(id);
    }
    
}
