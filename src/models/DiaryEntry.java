package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DiaryEntry {
    private final String title;
    private final String content;
    private final LocalDateTime timestamp;
    private final EmotionResult emotionResult;

    public DiaryEntry(String title, String content, LocalDateTime timestamp, EmotionResult emotionResult) {
        this.title = title == null ? "Untitled" : title.trim();
        this.content = content == null ? "" : content.trim();
        this.timestamp = timestamp == null ? LocalDateTime.now() : timestamp;
        this.emotionResult = emotionResult == null ? new EmotionResult() : emotionResult;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public EmotionResult getEmotionResult() {
        return emotionResult;
    }

    public String formatForStorage() {
        StringBuilder builder = new StringBuilder();
        builder.append("TITLE:").append(title).append(System.lineSeparator());
        builder.append("TIME:").append(timestamp.toString()).append(System.lineSeparator());
        builder.append("EMOTION:").append(emotionResult.getDominantEmotion().name()).append(System.lineSeparator());
        builder.append("SCORES:").append(emotionResult.compactScores()).append(System.lineSeparator());
        builder.append("CONTENT:").append(content.replace(System.lineSeparator(), "<br>")).append(System.lineSeparator());
        builder.append("---ENTRY---");
        return builder.toString();
    }

    public static DiaryEntry fromStorage(String serialized) {
        String[] lines = serialized.split(System.lineSeparator());
        String titleValue = "Untitled";
        String timeValue = LocalDateTime.now().toString();
        String emotionLine = "";
        String scoreLine = "";
        StringBuilder contentBuilder = new StringBuilder();
        for (String line : lines) {
            if (line.startsWith("TITLE:")) {
                titleValue = line.substring(6).trim();
            } else if (line.startsWith("TIME:")) {
                timeValue = line.substring(5).trim();
            } else if (line.startsWith("EMOTION:")) {
                emotionLine = line.substring(8).trim();
            } else if (line.startsWith("SCORES:")) {
                scoreLine = line.substring(7).trim();
            } else if (line.startsWith("CONTENT:")) {
                contentBuilder.append(line.substring(8).replace("<br>", System.lineSeparator()));
            }
        }
        EmotionResult parsedResult = EmotionResult.fromCompact(scoreLine, emotionLine);
        LocalDateTime parsedTime = LocalDateTime.parse(timeValue);
        return new DiaryEntry(titleValue, contentBuilder.toString(), parsedTime, parsedResult);
    }

    public String prettyPrint() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("[%s] %s%n%s%nEmotion: %s %s", formatter.format(timestamp), title, content,
                emotionResult.getDominantEmotion(), emotionResult.compactScores());
    }
}
