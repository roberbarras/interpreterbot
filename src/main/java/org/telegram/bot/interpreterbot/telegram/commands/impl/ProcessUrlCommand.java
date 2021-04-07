package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.springframework.stereotype.Component;
import org.telegram.bot.interpreterbot.model.*;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.entity.Garment;
import org.telegram.bot.interpreterbot.telegram.commands.Command;
import org.telegram.bot.interpreterbot.telegram.languages.Language;
import org.telegram.bot.interpreterbot.util.Constants;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class ProcessUrlCommand extends BaseCommand implements Command {

    private Map<String, String> RARE_CHARACTERS;

    @PostConstruct
    private void generateCharactersMap() {
        RARE_CHARACTERS = new HashMap<>();
        RARE_CHARACTERS.put("%C3%A1", "á");
        RARE_CHARACTERS.put("%C3%A9", "é");
        RARE_CHARACTERS.put("%C3%AD", "í");
        RARE_CHARACTERS.put("%C3%B3", "ó");
        RARE_CHARACTERS.put("%C3%BA", "ú");
        RARE_CHARACTERS.put("%C3%B1", "ñ");
        RARE_CHARACTERS.put("%C3%BC", "ü");
        RARE_CHARACTERS.put("%C3%81", "Á");
        RARE_CHARACTERS.put("%C3%89", "É");
        RARE_CHARACTERS.put("%C3%8D", "Í");
        RARE_CHARACTERS.put("%C3%93", "Ó");
        RARE_CHARACTERS.put("%C3%9A", "Ú");
        RARE_CHARACTERS.put("%C3%91", "Ñ");
        RARE_CHARACTERS.put("%C3%9C", "Ü");
    }

    @Override
    public MessageToSend process(MessageReceived messageReceived, Client client) {

        Language language = getLanguage(client.getUserLanguage());
        MessageToSend message = new MessageToSend();
        message.setChatId(String.valueOf(messageReceived.getChatId()));

        Optional.ofNullable(messageReceived.getText())
                .filter(elem -> elem.contains(Constants.HTTPS))
                .filter(elem -> elem.contains(Constants.URL))
                .map(elem -> elem.substring(elem.lastIndexOf(Constants.HTTPS)))
                .map(elem -> elem.replaceAll("\\?.*$", ""))
                .ifPresentOrElse((elem) -> {
                            String name = getNameFromUrl(elem);
                            message.setText(language.getRequestSizeMessage(name));
                            getTemporaryInfoService().updateStatusAndAddGarment(
                                    client.getClientId(),
                                    BotStatus.WAITING_SIZE,
                                    Garment.builder()
                                            .url(elem)
                                            .clientId(client.getClientId())
                                            .name(name)
                                            .creationDate(LocalDateTime.now())
                                            .enabled(true)
                                            .build()
                            );
                        },
                        () -> {
                            message.setText(language.getUrlErrorMessage());
                            getTemporaryInfoService().delete(client.getClientId());
                        });

        return message;
    }

    private String getNameFromUrl(String url) {

        Function<String, String> replaceCharacters = RARE_CHARACTERS.entrySet().stream()
                .reduce(
                        Function.identity(),
                        (f, e) -> s -> f.apply(s).replace(e.getKey(), e.getValue()),
                        Function::andThen
                );

        try {
            return replaceCharacters.apply(url.substring(url.lastIndexOf("/") + 1,
                    url.lastIndexOf("-p"))
                    .replace("-", " "));
        } catch (StringIndexOutOfBoundsException e) {
            try {
                return replaceCharacters.apply(url.substring(url.lastIndexOf("/") + 1,
                        url.indexOf(Constants.END_OF_URL)));
            } catch (StringIndexOutOfBoundsException e2) {
                return url;
            }
        }
    }
}
