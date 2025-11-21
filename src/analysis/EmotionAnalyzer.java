package analysis;

import models.EmotionResult;

public interface EmotionAnalyzer {
    EmotionResult analyze(String text);
}
