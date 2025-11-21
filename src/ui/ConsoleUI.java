package ui;

import analysis.EmotionAnalyzer;
import models.DiaryEntry;
import models.EmotionCategory;
import models.EmotionResult;
import storage.FileStorageManager;
import utils.ReportGenerator;
import utils.SuggestionProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final EmotionAnalyzer emotionAnalyzer;
    private final FileStorageManager storageManager;
    private final ReportGenerator reportGenerator;

    public ConsoleUI(EmotionAnalyzer emotionAnalyzer, FileStorageManager storageManager, ReportGenerator reportGenerator) {
        this.emotionAnalyzer = emotionAnalyzer;
        this.storageManager = storageManager;
        this.reportGenerator = reportGenerator;
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    handleNewEntry();
                    break;
                case "2":
                    handleViewEntries();
                    break;
                case "3":
                    handleSummary();
                    break;
                case "4":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-4.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("Emotion-Aware Diary Companion");
        System.out.println("1. Write a new entry");
        System.out.println("2. View previous entries");
        System.out.println("3. View emotion summary");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private void handleNewEntry() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.println("Write your entry (type END on a new line to finish):");
        StringBuilder contentBuilder = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if ("END".equalsIgnoreCase(line.trim())) {
                break;
            }
            contentBuilder.append(line).append(System.lineSeparator());
        }
        String content = contentBuilder.toString().trim();
        EmotionResult result = emotionAnalyzer.analyze(content);
        EmotionCategory dominant = result.getDominantEmotion();
        System.out.println("Detected emotion: " + dominant);
        System.out.println("Scores: " + result.compactScores());
        System.out.println("Suggestion: " + SuggestionProvider.getSuggestion(dominant));
        DiaryEntry entry = new DiaryEntry(title, content, LocalDateTime.now(), result);
        try {
            storageManager.saveEntry(entry);
            System.out.println("Entry saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save entry: " + e.getMessage());
        }
    }

    private void handleViewEntries() {
        String entries = storageManager.entriesAsText();
        System.out.println(entries);
    }

    private void handleSummary() {
        List<DiaryEntry> entries = storageManager.loadEntries();
        System.out.println(reportGenerator.generateEmotionSummary(entries));
        System.out.println(reportGenerator.generateLatestReport(entries));
    }
}
