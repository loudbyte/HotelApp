package com.epam.hotel.dao;

import java.util.Map;

public interface LanguageDAO {

    Map<Integer, String> getLanguageMap();

    long addLanguage(String locale);

    void updateLanguageById(long languageId, String newLocale);

    void deleteLanguageById(long languageId);

}