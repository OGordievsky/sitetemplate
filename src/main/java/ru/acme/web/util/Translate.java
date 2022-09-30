package ru.acme.web.util;

import com.ibm.icu.text.Transliterator;

public class Translate {
    private static final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

    public static String getCyrillicToLatin(String input){
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        return toLatinTrans.transliterate(input);
    }
}
