package storage;

import models.DiaryEntry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileStorageManager {
    private final Path entriesPath;

    public FileStorageManager(Path entriesPath) {
        this.entriesPath = entriesPath;
    }

    public void saveEntry(DiaryEntry entry) throws IOException {
        if (entry == null) {
            return;
        }
        if (!Files.exists(entriesPath)) {
            Path parent = entriesPath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            Files.createFile(entriesPath);
        }
        Files.writeString(entriesPath, entry.formatForStorage() + System.lineSeparator(), StandardCharsets.UTF_8,
                StandardOpenOption.APPEND);
    }

    public List<DiaryEntry> loadEntries() {
        if (!Files.exists(entriesPath)) {
            return new ArrayList<>();
        }
        try {
            String content = Files.readString(entriesPath);
            String[] rawEntries = content.split("---ENTRY---");
            List<DiaryEntry> entries = new ArrayList<>();
            for (String raw : rawEntries) {
                String trimmed = raw.trim();
                if (!trimmed.isEmpty()) {
                    try {
                        entries.add(DiaryEntry.fromStorage(trimmed));
                    } catch (Exception ex) {
                        entries.add(new DiaryEntry("Corrupted entry", trimmed, LocalDateTime.now(), null));
                    }
                }
            }
            return entries;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public String entriesAsText() {
        List<DiaryEntry> entries = loadEntries();
        if (entries.isEmpty()) {
            return "No diary entries found.";
        }
        return entries.stream().map(DiaryEntry::prettyPrint)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }
}
