# ASCII Diagram Suite

## Architecture Diagram
```
+--------+        +-------------------+        +--------------------+
|  User  |<-----> |     ConsoleUI     |<-----> | Emotion Analyzer   |
+--------+        +-------------------+        +--------------------+
                      |         ^                      |
                      v         |                      v
                 +-------------+|              +--------------------+
                 | FileStorage ||              | EmotionRulesLoader |
                 +-------------+|              +--------------------+
                      |         |                      |
                      v         |                      v
                +-------------------+        +--------------------+
                | DiaryEntry Model  |        | ReportGenerator    |
                +-------------------+        +--------------------+
```

## Use Case Diagram
```
        +-----------+
        |   User    |
        +-----+-----+
              |
     ---------------------
     |        |         |
+----------+  |  +------------------+
| Write    |  |  | View Summaries   |
| Entry    |  |  +------------------+
+----------+  |           |
              |     +------------------+
              |     | View Past Entries|
              |     +------------------+
```

## Class Diagram
```
+-------------------+        +----------------------+      +--------------------+
| EmotionAnalyzer   |<-------| RuleBasedEmotion...  |      | EmotionRulesLoader |
+-------------------+        +----------------------+      +--------------------+
|+analyze(text)     |        |+analyze(text)        |      |+load()             |
                     \                                     /
                      \                                   /
                 +-------------------+          +--------------------+
                 | EmotionResult     |          | EmotionCategory    |
                 +-------------------+          +--------------------+
                          ^                                ^
                          |                                |
                 +-------------------+          +----------------------+
                 | DiaryEntry        |<---------| FileStorageManager   |
                 +-------------------+          +----------------------+
                          ^                                ^
                          |                                |
                 +-------------------+          +----------------------+
                 | ConsoleUI         |--------->| ReportGenerator      |
                 +-------------------+          +----------------------+
```

## Sequence Diagram (Write Entry)
```
User -> ConsoleUI: choose "Write"
ConsoleUI -> User: prompt for title & lines
User -> ConsoleUI: provide text
ConsoleUI -> EmotionAnalyzer: analyze(text)
EmotionAnalyzer -> ConsoleUI: EmotionResult
ConsoleUI -> FileStorageManager: saveEntry(result)
FileStorageManager -> ConsoleUI: confirmation
ConsoleUI -> User: show scores & suggestion
```

## Workflow Diagram
```
[Start]
   |
[Display menu]
   |
{User choice}
   |--(1)-->[Capture entry]->[Analyze text]->[Persist]->[Show suggestion]-> back to menu
   |--(2)-->[Load entries]->[Display formatted list]-> back to menu
   |--(3)-->[Load entries]->[Generate summary]->[Show report]-> back to menu
   |--(4)-->[Exit]
```
