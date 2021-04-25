package org.telegram.bot.interpreterbot.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.telegram.bot.interpreterbot.model.internal.BotStatus;
import org.telegram.bot.interpreterbot.model.entity.Garment;
import org.telegram.bot.interpreterbot.model.internal.TemporaryInfo;

import java.util.Optional;

@Repository
public class TemporaryInfoRepository {

    @Value("${spring.redis.key.cache}")
    private String infoKey;

    private final RedisTemplate<Integer, TemporaryInfo> redisTemplate;

    private final HashOperations hashOperations;

    public TemporaryInfoRepository(RedisTemplate<Integer, TemporaryInfo> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(Integer clientId, TemporaryInfo temporaryInfo){
        hashOperations.put(infoKey, clientId, temporaryInfo);
    }

    public TemporaryInfo findById(Integer clientId){
        return Optional.ofNullable((TemporaryInfo)hashOperations.get(infoKey, clientId))
                .orElse(TemporaryInfo.builder().botStatus(BotStatus.WAITING_URL).build());
    }

    public void delete(Integer clientId){
        hashOperations.delete(infoKey, clientId);
    }

    public void updateStatus(Integer clientId, BotStatus botStatus){
        TemporaryInfo temporaryInfo = findById(clientId);
        temporaryInfo.setBotStatus(botStatus);
        save(clientId, temporaryInfo);
    }

    public void updateStatusAndAddGarment(Integer clientId, BotStatus botStatus, Garment garment){
        TemporaryInfo temporaryInfo = findById(clientId);
        temporaryInfo.setBotStatus(botStatus);
        temporaryInfo.setNewGarment(garment);
        save(clientId, temporaryInfo);
    }

}
