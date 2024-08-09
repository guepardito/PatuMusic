package es.guepardito.patumusic.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SongStringFormatter {
    /**
     * Funcion para formatear los nombres de las portadas a la normalizacion que impone discord para subir las imagenes
     * @param unformattedName string antes del formato
     * @return formated el string formateado
     */
    public static String formatCoverString(String unformattedName) {
        String formatted = unformattedName.toLowerCase();
        formatted = formatted.replace(" ", "_");

        formatted = Normalizer.normalize(formatted, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}");
        formatted = pattern.matcher(formatted).replaceAll("");

        formatted = formatted.replaceAll("[^a-z]", "_");

        return formatted;
    }

    /**
     * Verifica si un string contiene caracteres kanji, hangul u otros caracteres asiáticos.
     *
     * @param input El string de entrada.
     * @return true si contiene caracteres asiáticos, false en caso contrario.
     */
    public static boolean containsAsianCharacters(String input) {
        for (char c : input.toCharArray()) {
            Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(c);

            if (unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
                    unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A ||
                    unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B ||
                    unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C ||
                    unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D ||
                    unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS ||
                    unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT ||
                    unicodeBlock == Character.UnicodeBlock.HIRAGANA ||
                    unicodeBlock == Character.UnicodeBlock.KATAKANA ||
                    unicodeBlock == Character.UnicodeBlock.HANGUL_SYLLABLES ||
                    unicodeBlock == Character.UnicodeBlock.HANGUL_JAMO ||
                    unicodeBlock == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO ||
                    unicodeBlock == Character.UnicodeBlock.BOPOMOFO ||
                    unicodeBlock == Character.UnicodeBlock.KANBUN ||
                    unicodeBlock == Character.UnicodeBlock.KANGXI_RADICALS) {

                return true;
            }
        }
        return false;
    }


}
