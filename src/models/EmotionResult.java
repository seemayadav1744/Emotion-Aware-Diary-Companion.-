package models;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class EmotionResult {
    private final EnumMap<EmotionCategory, Integer> scores = new EnumMap<>(EmotionCategory.class);

    public EmotionResult() {
        for (EmotionCategory category : EmotionCategory.values()) {
            scores.put(category, 0);
        }
    }

    public void addScore(EmotionCategory category, int value) {
        scores.put(category, scores.getOrDefault(category, 0) + value);
    }

    public EmotionCategory getDominantEmotion() {
        EmotionCategory dominant = EmotionCategory.NEUTRAL;
        int max = Integer.MIN_VALUE;
        for (Map.Entry<EmotionCategory, Integer> entry : scores.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                dominant = entry.getKey();
            }
        }
        return dominant;
    }

    public Map<EmotionCategory, Integer> getScores() {
        return Collections.unmodifiableMap(scores);
    }

    public String compactScores() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<EmotionCategory, Integer> entry : scores.entrySet()) {
            builder.append(entry.getKey().name()).append('=').append(entry.getValue()).append(' ');
        }
        return builder.toString().trim();
    }

    public static EmotionResult fromCompact(String compact, String dominantLabel) {
        EmotionResult result = new EmotionResult();
        if (compact != null && !compact.isEmpty()) {
            String[] pairs = compact.split(" ");
            for (String pair : pairs) {
                if (pair.contains("=")) {
                    String[] parts = pair.split("=");
                    try {
                        EmotionCategory category = EmotionCategory.valueOf(parts[0]);
                        int score = Integer.parseInt(parts[1]);
                        result.scores.put(category, score);
                    } catch (Exception ignored) {
                        // Ignore malformed entries
                    }
                }
            }
        }
        // Ensure dominant marker is represented even if zero
        EmotionCategory dominant = EmotionCategory.fromLabel(dominantLabel);
        result.scores.putIfAbsent(dominant, result.scores.getOrDefault(dominant, 0));
        return result;
    }
}
