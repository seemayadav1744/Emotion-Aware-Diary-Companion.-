package utils;

import models.EmotionCategory;

import java.util.EnumMap;
import java.util.Map;

public final class SuggestionProvider {
    private static final Map<EmotionCategory, String> SUGGESTIONS = new EnumMap<>(EmotionCategory.class);

    static {
        SUGGESTIONS.put(EmotionCategory.HAPPY, "Keep embracing gratitude moments and share them with others.");
        SUGGESTIONS.put(EmotionCategory.SAD, "Consider journaling about the cause and plan a small uplifting activity.");
        SUGGESTIONS.put(EmotionCategory.ANGRY, "Pause for deep breaths or take a short walk to reset your energy.");
        SUGGESTIONS.put(EmotionCategory.FEAR, "List the facts you know and seek support from a trusted person.");
        SUGGESTIONS.put(EmotionCategory.NEUTRAL, "Reflect on highlights of the day to nurture awareness.");
    }

    private SuggestionProvider() {
    }

    public static String getSuggestion(EmotionCategory category) {
        return SUGGESTIONS.getOrDefault(category, "Observe your feelings and note what triggers them.");
    }
}
