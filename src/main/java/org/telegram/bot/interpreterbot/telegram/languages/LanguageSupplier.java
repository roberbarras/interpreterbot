package org.telegram.bot.interpreterbot.telegram.languages;

import org.telegram.bot.interpreterbot.model.internal.UserLanguage;
import org.telegram.bot.interpreterbot.telegram.languages.impl.EnglishText;
import org.telegram.bot.interpreterbot.telegram.languages.impl.SpanishText;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class LanguageSupplier {

    private static final Map<UserLanguage, Supplier<Language>> LANGUAGE_SUPPLIER;

    static {
        LANGUAGE_SUPPLIER = Map.of(
                UserLanguage.ES, SpanishText::new,
                UserLanguage.EN, EnglishText::new
        );
    }

    public static Language supplyLanguage(UserLanguage userLanguage) {
        return Optional.ofNullable(userLanguage)
                .map(LANGUAGE_SUPPLIER::get)
                .orElse(SpanishText::new)
                .get();
    }
}