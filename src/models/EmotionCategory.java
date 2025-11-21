package models;

public enum EmotionCategory {
    HAPPY,
    SAD,
    ANGRY,
    FEAR,
    NEUTRAL;

    public static EmotionCategory fromLabel(String label) {
        if (label == null) {
            return NEUTRAL;
        }
        switch (label.trim().toUpperCase()) {
            case "HAPPY":
                return HAPPY;
            case "SAD":
                return SAD;
            case "ANGRY":
                return ANGRY;
            case "FEAR":
            case "FEARFUL":
                return FEAR;
            default:
                return NEUTRAL;
        }
    }
}
