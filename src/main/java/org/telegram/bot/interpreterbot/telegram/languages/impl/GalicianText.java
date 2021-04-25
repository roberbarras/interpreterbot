package org.telegram.bot.interpreterbot.telegram.languages.impl;

import org.telegram.bot.interpreterbot.telegram.languages.Language;

public class GalicianText implements Language {

    @Override
    public String getBotDescription(String name) {
        return "Ola " + name + "! Son un robot para avisarche cando o tamaño e a roupa que necesitas están dispoñibles.";
    }

    @Override
    public String getNewGarmentMessage() {
        return "Envía a URL dun produto de Zara para crear unha alerta";
    }

    @Override
    public String getGarment() {
        return "Prenda";
    }

    @Override
    public String getSize() {
        return "talle";
    }

    @Override
    public String getRequestDeleteMessage() {
        return "Selecciona a prenda que queres eliminar";
    }

    @Override
    public String getConfirmDeleteMessage(String garment) {
        return "Eliminouse a alerta de '" + garment + "'";
    }

    @Override
    public String getConfirmDeleteErrorMessage(String input) {
        return "Non hai alerta co índice " + input;
    }

    @Override
    public String getLanguageMessage() {
        return "Selecciona un idioma";
    }

    @Override
    public String getConfirmChangeLanguage(String lang) {
        return "O idioma cambiouse correctamente a " + lang;
    }

    @Override
    public String getConfirmChangeLanguageErrorMessage() {
        return "Non se puido cambiar o idioma";
    }

    @Override
    public String getRequestSizeMessage(String garment) {
        return "Escolla o tamaño de '" + garment + "' para a que desexa crear unha alerta";
    }

    @Override
    public String getUrlErrorMessage() {
        return "Parece que a url está mal";
    }

    @Override
    public String getConfirmSaveGarmentMessage(String garment, String size) {
        return "A alerta para a prenda creouse correctamente '" + garment + "' e tamaño " + size;
    }

    @Override
    public String getEmptyGarmentListMessage() {
        return "Non hai produtos";
    }

    @Override
    public String getGeneralError() {
        return "Produciuse un erro";
    }

    @Override
    public String getNotPermissionMessage() {
        return "Non tes permiso";
    }

    @Override
    public String getAllowedClientMessage() {
        return "Benvido. Xa tes permisos para usar o bot";
    }

    @Override
    public String getBannedClientMessage() {
        return "Síntoo. Xa non tes permisos para usar o bot";
    }

    @Override
    public String getAlreadyAvailableMessage() {
        return "xa está dispoñible";
    }

    @Override
    public String getAlertMessage(String name, String url, String size) {
        return "A prenda <a href='" + url + "'>" + name + "</a> agora está dispoñible en tamaño " + size;
    }
}
