package org.telegram.bot.interpreterbot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.bot.interpreterbot.model.MessageReceived;
import org.telegram.bot.interpreterbot.model.MessageToSend;
import org.telegram.bot.interpreterbot.model.entity.MessageReceivedEntity;
import org.telegram.bot.interpreterbot.model.entity.MessageToSendEntity;
import org.telegram.bot.interpreterbot.repository.MessageReceivedRepository;
import org.telegram.bot.interpreterbot.repository.MessageToSendRepository;
import org.telegram.bot.interpreterbot.service.MessageService;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageReceivedRepository messageReceivedRepository;

    @Autowired
    private MessageToSendRepository messageToSendRepository;

    public void save(MessageToSend message) {
        messageToSendRepository.save(MessageToSendEntity.builder()
                .chatId(message.getChatId())
                .disableWebPagePreview(message.isDisableWebPagePreview())
                .parseMode(message.getParseMode())
                .text(message.getText())
                .creationDate(LocalDateTime.now())
                .build());
    }

    public void save(MessageReceived message) {
        messageReceivedRepository.save(MessageReceivedEntity.builder()
                .chatId(message.getChatId())
                .language(message.getLanguage())
                .clientId(message.getClientId())
                .name(message.getName())
                .text(message.getText())
                .creationDate(LocalDateTime.now())
                .build());
    }
}
