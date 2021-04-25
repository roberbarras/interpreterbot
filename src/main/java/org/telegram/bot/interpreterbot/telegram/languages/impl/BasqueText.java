package org.telegram.bot.interpreterbot.telegram.languages.impl;

import org.telegram.bot.interpreterbot.telegram.languages.Language;

public class BasqueText implements Language {

    @Override
    public String getBotDescription(String name) {
        return "Kaixo " + name + "! Behar duzun neurria eta jantzia eskuragarri daudenean jakinarazteko bot bat naiz.";
    }

    @Override
    public String getNewGarmentMessage() {
        return "Bidali Zara produktu baten URLa alerta sortzeko";
    }

    @Override
    public String getGarment() {
        return "Jantzia";
    }

    @Override
    public String getSize() {
        return "tamaina";
    }

    @Override
    public String getRequestDeleteMessage() {
        return "Aukeratu kendu nahi duzun jantzia";
    }

    @Override
    public String getConfirmDeleteMessage(String garment) {
        return "'" + garment + "' alerta kendu da";
    }

    @Override
    public String getConfirmDeleteErrorMessage(String input) {
        return "Indize honekin ez dago alertarik " + input;
    }

    @Override
    public String getLanguageMessage() {
        return "Aukeratu hizkuntza";
    }

    @Override
    public String getConfirmChangeLanguage(String lang) {
        return "Hizkuntza behar bezala aldatu da " + lang;
    }

    @Override
    public String getConfirmChangeLanguageErrorMessage() {
        return "Ezin izan da hizkuntza aldatu";
    }

    @Override
    public String getRequestSizeMessage(String garment) {
        return "Aukeratu alerta sortu nahi duzun '" + garment + "' tamaina";
    }

    @Override
    public String getUrlErrorMessage() {
        return "URLa okerra dela dirudi.";
    }

    @Override
    public String getConfirmSaveGarmentMessage(String garment, String size) {
        return "Jantziaren alerta behar bezala sortu da '" + garment + "' eta tamaina " + size;
    }

    @Override
    public String getEmptyGarmentListMessage() {
        return "Ez dago produkturik";
    }

    @Override
    public String getGeneralError() {
        return "Akats bat gertatu da";
    }

    @Override
    public String getNotPermissionMessage() {
        return "Ez duzu baimenik";
    }

    @Override
    public String getAllowedClientMessage() {
        return "Ongi etorri. Bot-a erabiltzeko baimenak dituzu dagoeneko";
    }

    @Override
    public String getBannedClientMessage() {
        return "Barkatu. Jada ez duzu bot-a erabiltzeko baimenik";
    }

    @Override
    public String getAlreadyAvailableMessage() {
        return "orain eskuragarri";
    }

    @Override
    public String getAlertMessage(String name, String url, String size) {
        return "Jantzia <a href='" + url + "'>" + name + "</a> tamaina eskuragarri dago orain " + size;
    }
}
