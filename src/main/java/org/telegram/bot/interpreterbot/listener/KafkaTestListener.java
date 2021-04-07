package org.telegram.bot.interpreterbot.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.bot.interpreterbot.model.MessageReceived;
import org.telegram.bot.interpreterbot.service.impl.BotServiceImpl;

@Service
public class KafkaTestListener {

    @Autowired
    private BotServiceImpl botService;

    @KafkaListener(topics = "${cloudkarafka.topic.receivemessage}", containerFactory = "customConsumerFactory")
    public void consumeJson(MessageReceived messageReceived) {
        botService.onUpdateReceived(messageReceived);
    }

}
