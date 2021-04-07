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

    @Value("${redis.key.client}")
    private String clientKey;

    private final RedisTemplate<Integer, Client> redisTemplate;

    private final HashOperations hashOperations;

    public ClientCacheRepository(RedisTemplate<Integer, Client> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(Integer clientId, Client client) {
        hashOperations.put(clientKey, clientId, client);
    }

    public Client findById(Integer clientId) {
        return Optional.ofNullable((Client) hashOperations.get(clientKey, clientId))
                .orElseThrow(NoSuchElementException::new);
    }

    public Client findByIdAndAuthTrue(Integer clientId) {
        return Optional.ofNullable((Client) hashOperations.get(clientKey, clientId))
                .filter(Client::isAuth)
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Integer clientId) {
        hashOperations.delete(clientKey, clientId);
    }

}
