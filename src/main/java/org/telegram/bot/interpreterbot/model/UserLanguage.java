package org.telegram.bot.interpreterbot.model;

public enum UserLanguage {
    ES("Español"),
    EN("English"),
    CAT("Català"),
    EUS("Euskara"),
    GA("Galego"),
    FR("Français");

    private final String description;

    UserLanguage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
