package org.telegram.bot.interpreterbot.telegram.languages;

import org.telegram.bot.interpreterbot.model.internal.UserLanguage;
import org.telegram.bot.interpreterbot.telegram.languages.impl.*;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class LanguageSupplier {

    private static final Map<UserLanguage, Supplier<Language>> LANGUAGE_SUPPLIER;

    static {
        LANGUAGE_SUPPLIER = Map.of(
                UserLanguage.ES, SpanishText::new,
                UserLanguage.EN, EnglishText::new,
                UserLanguage.CAT, CatalanText::new,
                UserLanguage.EUS, BasqueText::new,
                UserLanguage.GA, GalicianText::new,
                UserLanguage.FR, FrenchText::new
        );
    }

    public static Language supplyLanguage(UserLanguage userLanguage) {
        return Optional.ofNullable(userLanguage)
                .map(LANGUAGE_SUPPLIER::get)
                .orElse(SpanishText::new)
                .get();
    }
}