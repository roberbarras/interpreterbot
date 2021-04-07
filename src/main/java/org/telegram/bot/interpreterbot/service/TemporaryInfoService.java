package org.telegram.bot.interpreterbot.service;

import org.telegram.bot.interpreterbot.model.BotStatus;
import org.telegram.bot.interpreterbot.model.entity.Garment;
import org.telegram.bot.interpreterbot.model.TemporaryInfo;

public interface TemporaryInfoService {

    void save(Integer clientId, TemporaryInfo temporaryInfo);

    TemporaryInfo findById(Integer clientId);

    void delete(Integer clientId);

    void updateStatus(Integer clientId, BotStatus botStatus);

    void updateStatusAndAddGarment(Integer clientId, BotStatus botStatus, Garment garment);
}
