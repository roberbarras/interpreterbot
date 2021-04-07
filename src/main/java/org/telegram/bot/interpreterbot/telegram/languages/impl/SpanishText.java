package org.telegram.bot.interpreterbot.telegram.languages.impl;

import org.telegram.bot.interpreterbot.telegram.languages.Language;

public class SpanishText implements Language {

    @Override
    public String getBotDescription(String name) {
        return "Hola " + name + "! Soy un bot para avisarte cuando haya disponibilidad de la talla y prenda que necesitas.";
    }

    @Override
    public String getNewGarmentMessage() {
        return "Envía la URL de un producto de Zara para crear una alerta";
    }

    @Override
    public String getGarment() {
        return "Prenda";
    }

    @Override
    public String getSize() {
        return "talla";
    }

    @Override
    public String getRequestDeleteMessage() {
        return "Selecciona la prenda que desea eliminar";
    }

    @Override
    public String getConfirmDeleteMessage(String garment) {
        return "La alerta para '" + garment + "' ha sido eliminada";
    }

    @Override
    public String getConfirmDeleteErrorMessage(String input) {
        return "No hay ninguna alerta con el índice " + input;
    }

    @Override
    public String getLanguageMessage() {
        return "Selecciona un idioma";
    }

    @Override
    public String getConfirmChangeLanguage(String lang) {
        return "Se ha cambiado correctamente el idioma a " + lang;
    }

    @Override
    public String getConfirmChangeLanguageErrorMessage() {
        return "No se ha podido cambiar el idioma";
    }

    @Override
    public String getRequestSizeMessage(String garment) {
        return "Elige la talla de '" + garment + "' para la que deseas crear una alerta";
    }

    @Override
    public String getUrlErrorMessage() {
        return "La url parece ser incorrecta";
    }

    @Override
    public String getConfirmSaveGarmentMessage(String garment, String size) {
        return "Se ha creado correctamente la alerta para la prenda '" + garment + "' y talla " + size;
    }

    @Override
    public String getEmptyGarmentListMessage() {
        return "No hay productos";
    }

    @Override
    public String getGeneralError() {
        return "Se ha producido un error";
    }

    @Override
    public String getNotPermissionMessage() {
        return "No tienes permiso";
    }

    @Override
    public String getAllowedClientMessage() {
        return "Bienvenido. Ya tienes permisos para usar el bot";
    }

    @Override
    public String getBannedClientMessage() {
        return "Lo siento. Ya no tienes permisos para usar el bot";
    }

}
