package org.telegram.bot.interpreterbot.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.telegram.bot.interpreterbot.model.entity.Client;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class ClientCacheRepository {

    @Value("${spring.redis.key.client}")
    private String clientKey;

    private final RedisTemplate<Long, Client> redisTemplate;

    private final HashOperations hashOperations;

    public ClientCacheRepository(RedisTemplate<Long, Client> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(Long clientId, Client client) {
        hashOperations.put(clientKey, clientId, client);
    }

    public Client findById(Long clientId) {
        return Optional.ofNullable((Client) hashOperations.get(clientKey, clientId))
                .orElseThrow(NoSuchElementException::new);
    }

    public Client findByIdAndAuthTrue(Long clientId) {
        return Optional.ofNullable((Client) hashOperations.get(clientKey, clientId))
                .filter(Client::isAuth)
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Long clientId) {
        hashOperations.delete(clientKey, clientId);
    }

}
