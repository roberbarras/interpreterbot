package org.telegram.bot.interpreterbot.telegram.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.bot.interpreterbot.util.Constants;
import org.telegram.bot.interpreterbot.model.internal.BotStatus;
import org.telegram.bot.interpreterbot.telegram.commands.impl.*;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Component
public class CommandSupplier {

    @Autowired
    NewCommand newCommand;

    @Autowired
    StartCommand startCommand;

    @Autowired
    RequestDeleteCommand requestDeleteCommand;

    @Autowired
    ListCommand listCommand;

    @Autowired
    RequestChangeLanguageCommand requestChangeLanguageCommand;

    @Autowired
    ProcessUrlCommand processUrlCommand;

    @Autowired
    ProcessSizeCommand processSizeCommand;

    @Autowired
    ApplyDeleteCommand applyDeleteCommand;

    @Autowired
    ApplyLanguageCommand applyLanguageCommand;

    @Autowired
    OtherCommand otherCommand;

    private Map<String, Command> commandsMap;

    @PostConstruct
    private void generateMap () {
        commandsMap = Map.of(
                Constants.NEW_COMMAND, newCommand,
                Constants.START_COMMAND, startCommand,
                Constants.DELETE_COMMAND, requestDeleteCommand,
                Constants.LIST_COMMAND, listCommand,
                Constants.LANGUAGE_COMMAND, requestChangeLanguageCommand,
                BotStatus.WAITING_URL.name(), processUrlCommand,
                BotStatus.WAITING_SIZE.name(), processSizeCommand,
                BotStatus.CHOOSING_TO_DELETE.name(), applyDeleteCommand,
                BotStatus.CHOOSING_LANGUAGE.name(), applyLanguageCommand
        );
    }

    public Command supplyCommand(String commandType) {
        return Optional
                .ofNullable(commandsMap.get(commandType))
                .orElse(otherCommand);

    }
}
