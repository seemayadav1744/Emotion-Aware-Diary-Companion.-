import analysis.EmotionRulesLoader;
import analysis.EmotionAnalyzer;
import analysis.RuleBasedEmotionAnalyzer;
import storage.FileStorageManager;
import ui.ConsoleUI;
import utils.ReportGenerator;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path rulesPath = resolvePath("data/emotion_rules.txt");
        Path entriesPath = resolvePath("data/entries.txt");
        EmotionRulesLoader loader = new EmotionRulesLoader(rulesPath);
        EmotionAnalyzer analyzer = new RuleBasedEmotionAnalyzer(loader.load());
        FileStorageManager storageManager = new FileStorageManager(entriesPath);
        ReportGenerator reportGenerator = new ReportGenerator();
        ensureDataFiles(rulesPath, entriesPath);
        ConsoleUI consoleUI = new ConsoleUI(analyzer, storageManager, reportGenerator);
        consoleUI.start();
    }

    private static Path resolvePath(String relative) {
        Path cwd = Path.of(System.getProperty("user.dir"));
        if (cwd.getFileName() != null && "src".equalsIgnoreCase(cwd.getFileName().toString())) {
            Path parent = cwd.getParent();
            if (parent != null) {
                return parent.resolve(relative);
            }
        }
        return cwd.resolve(relative);
    }

    private static void ensureDataFiles(Path rulesPath, Path entriesPath) {
        try {
            if (entriesPath.getParent() != null && !Files.exists(entriesPath.getParent())) {
                Files.createDirectories(entriesPath.getParent());
            }
            if (!Files.exists(entriesPath)) {
                Files.createFile(entriesPath);
            }
            if (rulesPath.getParent() != null && !Files.exists(rulesPath.getParent())) {
                Files.createDirectories(rulesPath.getParent());
            }
            if (!Files.exists(rulesPath)) {
                Files.createFile(rulesPath);
            }
        } catch (Exception ignored) {
            // File operations are managed defensively elsewhere.
        }
    }
}
