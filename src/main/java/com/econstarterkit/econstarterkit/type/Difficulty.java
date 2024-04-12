package com.econstarterkit.econstarterkit.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Difficulty {
    NOT_CHECK("not_check"),
    EASY("easy"),
    NORMAL("normal"),
    HARD("hard"),
    VERY_HARD("very_hard"),
    ALL("all");

    private final String difficultyText;

    Difficulty(String difficultyText) {
        this.difficultyText = difficultyText;
    }

    public static Difficulty fromString(String difficultyText) {
        return Difficulty.valueOf(difficultyText.toUpperCase());
    }

    @JsonValue
    public String getText() {
        return difficultyText;
    }
}
