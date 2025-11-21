# Emotion-Aware Health Companion (Java)

A console-based personal diary that applies rule-based emotional analysis to each entry, offers supportive suggestions, and stores everything on disk for later review.

## Features
- Guided diary writing flow with END-based multi-line input.
- Rule-based keyword scoring for Happy, Sad, Angry, Fear, and Neutral emotions.
- Automatic suggestions tailored to the dominant emotion per entry.
- Persistent storage in `data/entries.txt` with reload and display utilities.
- Emotion summary analytics plus latest entry highlights via the report generator.
- Configurable rules via `data/emotion_rules.txt` with safe fallbacks.

## Functional Requirements
1. Capture diary entries with timestamps, emotion scores, and suggestions.
2. Analyze text using keyword rules and present emotion scores to the user.
3. Store, reload, and display previous diary entries from disk.
4. Produce summaries that count dominant emotions and highlight the most recent entry.

## Non-Functional Requirements
- **Usability:** Menu-driven console experience with clear prompts and validation.
- **Reliability:** Default rules and safe file handling keep the app running even if files are missing.
- **Maintainability:** Modular package structure (models, analysis, storage, ui, utils) and interface-based analyzer.
- **Portability:** Uses only standard Java libraries, runnable on any JDK 11+ environment.
- **Performance:** Lightweight string processing suited for short diary entries.

## Tech Stack
- Java 17 (standard libraries only)
- File-based persistence (UTF-8 text)

## Folder Structure
```
Emotion-Aware Diary Companion/
├── data/
│   ├── emotion_rules.txt
│   └── entries.txt
├── docs/
│   └── diagrams.md
├── src/
│   ├── Main.java
│   ├── analysis/
│   │   ├── EmotionAnalyzer.java
│   │   ├── EmotionRulesLoader.java
│   │   └── RuleBasedEmotionAnalyzer.java
│   ├── models/
│   │   ├── DiaryEntry.java
│   │   ├── EmotionCategory.java
│   │   └── EmotionResult.java
│   ├── storage/
│   │   └── FileStorageManager.java
│   ├── ui/
│   │   └── ConsoleUI.java
│   └── utils/
│       ├── ReportGenerator.java
│       ├── SuggestionProvider.java
│       └── TextUtils.java
├── README.md
└── statement.md
```

## How to Run
1. Open a terminal in the project root.
2. Navigate into the `src` directory: `cd src`
3. Compile: `javac Main.java`
4. Run: `java Main`
5. Follow the interactive menu to add entries, show history, or view summaries.

## Sample I/O
```
Emotion-Aware Diary Companion
1. Write a new entry
2. View previous entries
3. View emotion summary
4. Exit
Choose an option: 1
Title: Bright Morning
Write your entry (type END on a new line to finish):
I felt so grateful and happy during breakfast
END
Detected emotion: HAPPY
Scores: HAPPY=5 SAD=0 ANGRY=0 FEAR=0 NEUTRAL=0
Suggestion: Keep embracing gratitude moments and share them with others.
Entry saved successfully.
```

---

##  How to Run
1. Clone this repository  
2. Open in VS Code / IntelliJ / Cursor  
3. Compile all `.java` files  
4. Run `Main.java`  

---

##  Testing
Includes:
- Input validation test  
- File write/read testing  
- Emotion mapping tests 
-Bmylr rishi 

---

##  Future Enhancements
- GUI using JavaFX  
- ML-based emotion prediction  
- Graphical emotional trend visualization  

---

##  Author
Bongu Rishi  


