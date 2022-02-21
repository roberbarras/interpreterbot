package org.telegram.bot.interpreterbot.telegram.languages.impl;

import org.telegram.bot.interpreterbot.telegram.languages.Language;

public class CatalanText implements Language {

    @Override
    public String getBotDescription(String name) {
        return "Hola " + name + "! Sóc un bot per avisar-te quan hi hagi disponibilitat de la talla i peça que necessites.";
    }

    @Override
    public String getNewGarmentMessage() {
        return "Envia la URL d'un producte de Zara per crear un avís";
    }

    @Override
    public String getGarment() {
        return "Penyora";
    }

    @Override
    public String getSize() {
        return "talla";
    }

    @Override
    public String getRequestDeleteMessage() {
        return "Selecciona la peça que vols suprimir";
    }

    @Override
    public String getConfirmDeleteMessage(String garment) {
        return "L'alerta per '" + garment + "' ha estat eliminada";
    }

    @Override
    public String getConfirmDeleteErrorMessage(String input) {
        return "No hi ha cap alerta amb l'índex " + input;
    }

    @Override
    public String getLanguageMessage() {
        return "Selecciona una llengua";
    }

    @Override
    public String getConfirmChangeLanguage(String lang) {
        return "S'ha canviat correctament l'idioma a " + lang;
    }

    @Override
    public String getConfirmChangeLanguageErrorMessage() {
        return "No s'ha pogut canviar l'idioma";
    }

    @Override
    public String getRequestSizeMessage(String garment) {
        return "Tria la talla de '" + garment + "' per a la que vols crear un avís";
    }

    @Override
    public String getUrlErrorMessage() {
        return "L'URL sembla ser incorrecta";
    }

    @Override
    public String getConfirmSaveGarmentMessage(String garment, String size) {
        return "S'ha creat correctament l'alerta per la peça '" + garment + "' i talla " + size;
    }

    @Override
    public String getEmptyGarmentListMessage() {
        return "No hi ha productes";
    }

    @Override
    public String getGeneralError() {
        return "S'ha produït un error";
    }

    @Override
    public String getNotPermissionMessage() {
            return "No tens permís";
    }

    @Override
    public String getAllowedClientMessage() {
        return "Benvingut. Ja tens permisos per utilitzar el bot";
    }

    @Override
    public String getBannedClientMessage() {
        return "Ho sento. Ja no tens permisos per utilitzar el bot";
    }

    @Override
    public String getAlreadyAvailableMessage() {
        return "ja disponible";
    }

    @Override
    public String getAlertMessage(String name, String url, String size) {
        return "La peça <a href='" + url + "'>" + name + "</a> ja està disponible a la talla " + size;
    }

    @Override
    public String getCleanMessage() {
        return "S'han eliminat les peces inactives";
    }
}
