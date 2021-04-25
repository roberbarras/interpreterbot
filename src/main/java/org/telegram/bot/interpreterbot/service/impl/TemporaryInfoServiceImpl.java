package org.telegram.bot.interpreterbot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.bot.interpreterbot.model.internal.BotStatus;
import org.telegram.bot.interpreterbot.model.entity.Garment;
import org.telegram.bot.interpreterbot.model.internal.TemporaryInfo;
import org.telegram.bot.interpreterbot.repository.TemporaryInfoRepository;
import org.telegram.bot.interpreterbot.service.TemporaryInfoService;

@Service
public class TemporaryInfoServiceImpl implements TemporaryInfoService {

    @Autowired
    TemporaryInfoRepository temporaryInfoRepository;

    public void save(Integer clientId, TemporaryInfo temporaryInfo) {
        temporaryInfoRepository.save(clientId, temporaryInfo);
    }

    public TemporaryInfo findById(Integer clientId) {
        return temporaryInfoRepository.findById(clientId);
    }

    public void delete(Integer clientId) {
        temporaryInfoRepository.delete(clientId);
    }

    public void updateStatus(Integer clientId, BotStatus botStatus) {
        temporaryInfoRepository.updateStatus(clientId, botStatus);
    }

    public void updateStatusAndAddGarment(Integer clientId, BotStatus botStatus, Garment garment) {
        temporaryInfoRepository.updateStatusAndAddGarment(clientId, botStatus, garment);
    }
}
