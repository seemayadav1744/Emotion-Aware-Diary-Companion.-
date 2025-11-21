package utils;

import models.DiaryEntry;
import models.EmotionCategory;

import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
    public String generateEmotionSummary(List<DiaryEntry> entries) {
        if (entries == null || entries.isEmpty()) {
            return "No entries available yet.";
        }
        EnumMap<EmotionCategory, Integer> totals = new EnumMap<>(EmotionCategory.class);
        for (EmotionCategory category : EmotionCategory.values()) {
            totals.put(category, 0);
        }
        for (DiaryEntry entry : entries) {
            EmotionCategory dominant = entry.getEmotionResult().getDominantEmotion();
            totals.put(dominant, totals.get(dominant) + 1);
        }
        StringBuilder builder = new StringBuilder("Emotion Summary: ");
        for (Map.Entry<EmotionCategory, Integer> entry : totals.entrySet()) {
            builder.append(entry.getKey().name()).append('=').append(entry.getValue()).append(' ');
        }
        return builder.toString().trim();
    }

    public String generateLatestReport(List<DiaryEntry> entries) {
        if (entries == null || entries.isEmpty()) {
            return "No entries to report.";
        }
        DiaryEntry latest = entries.get(entries.size() - 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Latest entry on " + formatter.format(latest.getTimestamp()) + " was predominantly "
                + latest.getEmotionResult().getDominantEmotion() + '.';
    }
}
