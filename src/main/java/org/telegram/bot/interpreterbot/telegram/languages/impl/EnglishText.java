package org.telegram.bot.interpreterbot.telegram.languages.impl;

import org.telegram.bot.interpreterbot.telegram.languages.Language;

public class EnglishText implements Language {

    @Override
    public String getBotDescription(String name) {
        return "Hello " + name + "! I'm a bot to let you know when the size and garment you need is available.";
    }

    @Override
    public String getNewGarmentMessage() {
        return "Send the URL of a Zara product to create an alert";
    }

    @Override
    public String getGarment() {
        return "Garment";
    }

    @Override
    public String getSize() {
        return "size";
    }

    @Override
    public String getRequestDeleteMessage() {
        return "Choose the garment you want to remove";
    }

    @Override
    public String getConfirmDeleteMessage(String garment) {
        return "The alert for '" + garment + "' has been removed";
    }

    @Override
    public String getConfirmDeleteErrorMessage(String input) {
        return "There is no alert with the index " + input;
    }

    @Override
    public String getLanguageMessage() {
        return "Select your language";
    }

    @Override
    public String getConfirmChangeLanguage(String lang) {
        return "The language has been changed to " + lang;
    }

    @Override
    public String getConfirmChangeLanguageErrorMessage() {
        return "Language could not be changed";
    }

    @Override
    public String getRequestSizeMessage(String garment) {
        return "Choose the size for '" + garment + "' which you want the alert";
    }

    @Override
    public String getUrlErrorMessage() {
        return "The url seems to be wrong";
    }

    @Override
    public String getConfirmSaveGarmentMessage(String garment, String size) {
        return "The alert for garment '" + garment + "' and size '" + size + "' has been successfully created";
    }

    @Override
    public String getEmptyGarmentListMessage() {
        return "There are no products";
    }

    @Override
    public String getGeneralError() {
        return "An error has ocurred";
    }

    @Override
    public String getNotPermissionMessage() {
        return "You do not have permission";
    }

    @Override
    public String getAllowedClientMessage() {
        return "Welcome. You already have permissions to use the bot";
    }

    @Override
    public String getBannedClientMessage() {
        return "I'm sorry. You no longer have permissions to use the bot";
    }

    @Override
    public String getAlreadyAvailableMessage() {
        return "now available";
    }

    @Override
    public String getAlertMessage(String name, String url, String size) {
        return "Garment <a href='" + url + "'>" + name + "</a> is now available in size " + size;
    }

}
