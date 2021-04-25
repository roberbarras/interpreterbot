package org.telegram.bot.interpreterbot.telegram.languages.impl;

import org.telegram.bot.interpreterbot.telegram.languages.Language;

public class FrenchText implements Language {

    @Override
    public String getBotDescription(String name) {
        return "Bonjour " + name + "! Je suis un robot qui vous prévient lorsque la taille et le vêtement dont vous avez besoin sont disponibles.";
    }

    @Override
    public String getNewGarmentMessage() {
        return "Soumettre l'URL d'un produit Zara pour créer une alerte";
    }

    @Override
    public String getGarment() {
        return "Pièce";
    }

    @Override
    public String getSize() {
        return "taille";
    }

    @Override
    public String getRequestDeleteMessage() {
        return "Sélectionnez le vêtement que vous voulez supprimer";
    }

    @Override
    public String getConfirmDeleteMessage(String garment) {
        return "L'alerte pour '" + garment + "' a été supprimée";
    }

    @Override
    public String getConfirmDeleteErrorMessage(String input) {
        return "Il n'y a pas d'alerte avec l'indice " + input;
    }

    @Override
    public String getLanguageMessage() {
        return "Sélectionnez une langue";
    }

    @Override
    public String getConfirmChangeLanguage(String lang) {
        return "La langue a été modifiée avec succès en " + lang;
    }

    @Override
    public String getConfirmChangeLanguageErrorMessage() {
        return "Impossible de changer de langue";
    }

    @Override
    public String getRequestSizeMessage(String garment) {
        return "Choisissez la taille de '" + garment + "' pour laquelle vous voulez créer une alerte";
    }

    @Override
    public String getUrlErrorMessage() {
        return "L'url semble être incorrecte";
    }

    @Override
    public String getConfirmSaveGarmentMessage(String garment, String size) {
        return "Le signalement a été créé avec succès pour le vêtement '" + garment + "' et la taille " + size;
    }

    @Override
    public String getEmptyGarmentListMessage() {
        return "Il n'y a pas de produits";
    }

    @Override
    public String getGeneralError() {
        return "Une erreur s'est produite";
    }

    @Override
    public String getNotPermissionMessage() {
        return "Vous n'avez pas la permission";
    }

    @Override
    public String getAllowedClientMessage() {
        return "Bienvenue. Vous avez déjà les autorisations pour utiliser le bot";
    }

    @Override
    public String getBannedClientMessage() {
        return "Je suis désolé, vous n'avez plus la permission d'utiliser le robot.";
    }

    @Override
    public String getAlreadyAvailableMessage() {
        return "maintenant disponible";
    }

    @Override
    public String getAlertMessage(String name, String url, String size) {
        return "Le vêtement <a href='" + url + "'>" + name + "</a> est maintenant disponible dans la taille suivante " + size;
    }

}
