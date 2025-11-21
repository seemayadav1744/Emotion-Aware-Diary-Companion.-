package analysis;

import models.EmotionCategory;
import models.EmotionResult;
import utils.TextUtils;

import java.util.List;
import java.util.Map;

public class RuleBasedEmotionAnalyzer implements EmotionAnalyzer {
    private final Map<EmotionCategory, Map<String, Integer>> rules;

    public RuleBasedEmotionAnalyzer(Map<EmotionCategory, Map<String, Integer>> rules) {
        this.rules = rules;
    }

    @Override
    public EmotionResult analyze(String text) {
        EmotionResult result = new EmotionResult();
        String cleaned = TextUtils.normalize(text);
        List<String> tokens = TextUtils.tokenize(cleaned);
        for (String token : tokens) {
            for (EmotionCategory category : rules.keySet()) {
                Integer weight = rules.get(category).get(token);
                if (weight != null) {
                    result.addScore(category, weight);
                }
            }
        }
        if (tokens.isEmpty()) {
            result.addScore(EmotionCategory.NEUTRAL, 1);
        }
        return result;
    }
}
