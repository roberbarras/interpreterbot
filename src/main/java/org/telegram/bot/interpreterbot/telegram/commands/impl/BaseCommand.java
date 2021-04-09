package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.bot.interpreterbot.model.kafka.MessageToSend;
import org.telegram.bot.interpreterbot.model.internal.UserLanguage;
import org.telegram.bot.interpreterbot.service.GarmentService;
import org.telegram.bot.interpreterbot.service.TemporaryInfoService;
import org.telegram.bot.interpreterbot.telegram.languages.Language;
import org.telegram.bot.interpreterbot.telegram.languages.LanguageSupplier;

public abstract class BaseCommand {

    @Autowired
    private TemporaryInfoService temporaryInfoService;

    @Autowired
    private GarmentService garmentService;


    public TemporaryInfoService getTemporaryInfoService() {
        return temporaryInfoService;
    }


    public GarmentService getGarmentService() {
        return garmentService;
    }

    public Language getLanguage(UserLanguage language) {
        return LanguageSupplier.supplyLanguage(language);
    }

    public MessageToSend prepareMessage(String text, long clientId, boolean webPagePreview) {
        return MessageToSend.builder()
                .chatId(String.valueOf(clientId))
                .disableWebPagePreview(webPagePreview)
                .text(text).build();
    }

    public MessageToSend prepareMessage(String text, long clientId, boolean webPagePreview, String parseMode) {
        return MessageToSend.builder()
                .chatId(String.valueOf(clientId))
                .disableWebPagePreview(webPagePreview)
                .parseMode(parseMode)
                .text(text)
                .build();
    }

}
