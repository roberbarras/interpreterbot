package org.telegram.bot.interpreterbot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.internal.UserLanguage;
import org.telegram.bot.interpreterbot.model.kafka.AvailableSizesResponse;
import org.telegram.bot.interpreterbot.model.kafka.GarmentAdvice;
import org.telegram.bot.interpreterbot.model.kafka.MessageReceived;
import org.telegram.bot.interpreterbot.model.kafka.MessageToSend;
import org.telegram.bot.interpreterbot.service.BotService;
import org.telegram.bot.interpreterbot.telegram.commands.Command;
import org.telegram.bot.interpreterbot.telegram.commands.CommandSupplier;
import org.telegram.bot.interpreterbot.telegram.languages.LanguageSupplier;
import org.telegram.bot.interpreterbot.util.Constants;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BotServiceImpl implements BotService {

    @Autowired
    private CommandSupplier commandSupplier;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private MessageServiceImpl messageService;

    @Autowired
    private KafkaTemplate<String, MessageToSend> messageToSendProducer;

    @Value("${cloudkarafka.topic.sendmessage}")
    private String sendMessageTopic;

    @Value("${bot.adminchatid}")
    private Long adminChatId;

    @Override
    public void onUpdateReceived(MessageReceived messageReceived) {
        Optional.of(messageReceived)
                .filter(mes -> mes.getText() != null)
                .ifPresent(elem -> {
                    Optional.of(elem.getChatId())
                            .filter(chat -> chat.equals(adminChatId))
                            .ifPresentOrElse((message) -> adminMessageReceived(messageReceived),
                                    () -> clientMessageReceived(messageReceived));});
        CompletableFuture.runAsync(() -> messageService.save(messageReceived));
    }

    @Override
    public void processAvailableSizes(AvailableSizesResponse availableSizesResponse) {
        MessageToSend message = MessageToSend.builder().chatId(availableSizesResponse.getClientId())
                .text(sizesMapToMessageText(availableSizesResponse.getSizes(),
                        availableSizesResponse.getLanguage()))
                .build();

        sendMessageToKafka(message);
    }

    @Override
    public void processNewAlert(GarmentAdvice garment) {
        Client client = clientService.findById(garment.getClientId());
        MessageToSend message = MessageToSend.builder()
                .text(LanguageSupplier.supplyLanguage(client.getUserLanguage())
                        .getAlertMessage(garment.getName(), garment.getUrl(), garment.getSize()))
                .disableWebPagePreview(false)
                .parseMode(Constants.PARSE_MODE_HTML)
                .chatId(String.valueOf(garment.getClientId()))
                .build();

        sendMessageToKafka(message);
    }

    private String sizesMapToMessageText(Map<String, Boolean> sizes, UserLanguage language) {
        StringBuilder text = new StringBuilder();
        sizes.entrySet().stream()
                .filter(elem -> !elem.getValue())
                .map(elem -> text.append("/" + Normalizer
                        .normalize(elem.getKey(), Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "")
                        .replace(" ", "_") + "\n"))
                .collect(Collectors.toList());
        sizes.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(elem -> text.append(Normalizer
                        .normalize(elem.getKey(), Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "") + " (" +
                        LanguageSupplier.supplyLanguage(language).getAlreadyAvailableMessage()
                        + ")\n"))
                .collect(Collectors.toList());
        return text.toString();
    }

    private void clientMessageReceived(MessageReceived messageReceived) {
        Client senderClient = clientService.findById(messageReceived.getClientId());
        MessageToSend messageToSend = Optional.ofNullable(senderClient)
                .map(client -> checkIfClientExists(client, messageReceived))
                .orElseGet(() -> saveClientRequestAuthorizationAndSendError(messageReceived));
        sendMessageToKafka(messageToSend);
    }

    private void adminMessageReceived(MessageReceived messageReceived) {
        try {
            String[] message = messageReceived.getText().split("_");
            String command = message[0].replace("/", "");
            int client = Integer.parseInt(message[1]);

            Optional.of((Constants.ALLOW_COMMAND).equals(command))
                    .filter(e -> e)
                    .ifPresentOrElse(allow -> {
                                clientService.clientPermission(client, true);
                                sendMessageToKafka(allowedClientMessage(client));
                            },
                            () -> {
                                clientService.clientPermission(client, false);
                                sendMessageToKafka(bannedClientMessage(client));
                            });
        } catch (RuntimeException e) {
            log.error("Error interpreting administrator's message");
            sendMessageToKafka(MessageToSend.builder()
                    .text(Constants.ADMIN_ERROR_MESSAGE)
                    .chatId(adminChatId.toString()).build());
        }
    }

    private MessageToSend checkIfClientExists(Client client, MessageReceived messageReceived) {
        return Optional.of(client)
                .filter(Client::isAuth)
                .map(authOkClient -> processClientMessage(messageReceived, authOkClient))
                .orElseGet(() -> sendErrorMessage(client, messageReceived));
    }

    private MessageToSend processClientMessage(MessageReceived messageReceived, Client client) {
        Command command = commandSupplier
                .supplyCommand(Optional.ofNullable(messageReceived.getText()).orElse(""));
        return command.process(messageReceived, client);
    }

    private MessageToSend sendErrorMessage(Client senderClient, MessageReceived messageReceived) {
        return MessageToSend.builder()
                .text(LanguageSupplier.supplyLanguage(senderClient.getUserLanguage()).getNotPermissionMessage())
                .chatId(String.valueOf(messageReceived.getChatId())).build();
    }

    private MessageToSend saveClientRequestAuthorizationAndSendError(MessageReceived messageReceived) {
        Client clientToSave = Client
                .builder()
                .clientId(messageReceived.getClientId())
                .name(messageReceived.getName())
                .auth(false)
                .userLanguage(UserLanguage.valueOf(messageReceived.getLanguage().toUpperCase()))
                .creationDate(LocalDateTime.now())
                .build();
        clientService.save(clientToSave);
        sendMessageToKafka(sendAdminMessage(clientToSave));
        return sendErrorMessage(clientToSave, messageReceived);
    }

    private MessageToSend sendAdminMessage(Client clientToSave) {
        return MessageToSend.builder()
                .text("El usuario " + clientToSave.getName() + " con ID " + clientToSave.getClientId() +
                        " está intentando usar el bot. ¿Desea permitirlo? \n/allow_" + clientToSave.getClientId()
                        + "\n/denny_" + clientToSave.getClientId())
                .chatId(adminChatId.toString())
                .build();
    }

    private MessageToSend allowedClientMessage(int clientId) {
        Client client = clientService.findById(clientId);
        return MessageToSend.builder()
                .text(LanguageSupplier.supplyLanguage(client.getUserLanguage())
                        .getAllowedClientMessage())
                .chatId(String.valueOf(clientId))
                .build();
    }

    private MessageToSend bannedClientMessage(int clientId) {
        Client client = clientService.findById(clientId);
        return MessageToSend.builder()
                .text(LanguageSupplier.supplyLanguage(client.getUserLanguage())
                        .getBannedClientMessage())
                .chatId(String.valueOf(clientId))
                .build();
    }

    private void sendMessageToKafka(MessageToSend messageToSend) {
        messageToSendProducer.send(sendMessageTopic, messageToSend);
        CompletableFuture.runAsync(() -> messageService.save(messageToSend));
    }
}
