package org.telegram.bot.interpreterbot.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.telegram.bot.interpreterbot.model.entity.Garment;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class GarmentCacheRepository {

    @Value("${redis.key.garment}")
    private String garmentKey;

    private final RedisTemplate<Integer, List<Garment>> redisTemplate;

    private final HashOperations hashOperations;

    public GarmentCacheRepository(RedisTemplate<Integer, List<Garment>> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(Integer clientId, List<Garment> garmentList) {
        hashOperations.put(garmentKey, clientId, garmentList);
    }

    public List<Garment> findById(Integer clientId) {
        return Optional.ofNullable((List<Garment>) hashOperations.get(garmentKey, clientId))
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Integer clientId) {
        hashOperations.delete(garmentKey, clientId);
    }
}
