package ua.com.alevel.config;

import lombok.Getter;

@Getter
public enum FileType {

    JSON_TYPE("users.json"),
    CSV_TYPE("users.csv");

    private final String path;

    FileType(String path) {
        this.path = path;
    }
}
