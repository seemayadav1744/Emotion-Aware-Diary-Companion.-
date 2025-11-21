package utils;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class TextUtils {
    private TextUtils() {
    }

    public static String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.toLowerCase(Locale.ENGLISH).replaceAll("[^a-zA-Z\\s]", " ").trim();
    }

    public static List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return tokens;
        }
        BreakIterator iterator = BreakIterator.getWordInstance(Locale.ENGLISH);
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String word = text.substring(start, end).trim();
            if (!word.isEmpty() && word.chars().allMatch(Character::isLetter)) {
                tokens.add(word);
            }
        }
        return tokens;
    }
}
