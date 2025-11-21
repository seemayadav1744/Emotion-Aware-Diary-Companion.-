package analysis;

import models.EmotionCategory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmotionRulesLoader {
    private final Path rulesPath;

    public EmotionRulesLoader(Path rulesPath) {
        this.rulesPath = rulesPath;
    }

    public Map<EmotionCategory, Map<String, Integer>> load() {
        EnumMap<EmotionCategory, Map<String, Integer>> rules = new EnumMap<>(EmotionCategory.class);
        for (EmotionCategory category : EmotionCategory.values()) {
            rules.put(category, new HashMap<>());
        }
        if (rulesPath == null || !Files.exists(rulesPath)) {
            applyDefaultRules(rules);
            return rules;
        }
        try {
            List<String> lines = Files.readAllLines(rulesPath);
            for (String line : lines) {
                if (line == null || line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }
                String[] parts = line.split(":");
                if (parts.length != 3) {
                    continue;
                }
                EmotionCategory category = EmotionCategory.fromLabel(parts[0]);
                String keyword = parts[1].trim().toLowerCase();
                int weight;
                try {
                    weight = Integer.parseInt(parts[2].trim());
                } catch (NumberFormatException ex) {
                    weight = 1;
                }
                rules.get(category).put(keyword, weight);
            }
        } catch (IOException e) {
            applyDefaultRules(rules);
        }
        return rules;
    }

    private void applyDefaultRules(EnumMap<EmotionCategory, Map<String, Integer>> rules) {
        rules.get(EmotionCategory.HAPPY).put("happy", 3);
        rules.get(EmotionCategory.HAPPY).put("sunny", 2);
        rules.get(EmotionCategory.SAD).put("blue", 2);
        rules.get(EmotionCategory.ANGRY).put("rage", 3);
        rules.get(EmotionCategory.FEAR).put("nervous", 2);
        rules.get(EmotionCategory.NEUTRAL).put("fine", 1);
    }
}
