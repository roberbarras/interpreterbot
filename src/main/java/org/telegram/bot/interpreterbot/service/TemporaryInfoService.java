package org.telegram.bot.interpreterbot.service;

import org.telegram.bot.interpreterbot.model.internal.BotStatus;
import org.telegram.bot.interpreterbot.model.entity.Garment;
import org.telegram.bot.interpreterbot.model.internal.TemporaryInfo;

public interface TemporaryInfoService {

    void save(Long clientId, TemporaryInfo temporaryInfo);

    TemporaryInfo findById(Long clientId);

    void delete(Long clientId);

    void updateStatus(Long clientId, BotStatus botStatus);

    void updateStatusAndAddGarment(Long clientId, BotStatus botStatus, Garment garment);
}
