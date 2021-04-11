package org.telegram.bot.interpreterbot.telegram.languages;

public interface Language {

    String getBotDescription(String name);
    String getNewGarmentMessage();
    String getGarment();
    String getSize();
    String getRequestDeleteMessage();
    String getConfirmDeleteMessage(String garment);
    String getConfirmDeleteErrorMessage(String input);
    String getLanguageMessage();
    String getConfirmChangeLanguage(String lang);
    String getConfirmChangeLanguageErrorMessage();
    String getRequestSizeMessage(String garment);
    String getUrlErrorMessage();
    String getConfirmSaveGarmentMessage(String garment, String size);
    String getEmptyGarmentListMessage();
    String getGeneralError();
    String getNotPermissionMessage();
    String getAllowedClientMessage();
    String getBannedClientMessage();
    String getAlreadyAvailableMessage();

}
