package com.econstarterkit.econstarterkit.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProblemType {
    WORD("word"),
    HISTORY("history"),
    OTHER("other"),
    ALL("all");

    private final String problemTypeText;

    ProblemType(String problemTypeText) {
        this.problemTypeText = problemTypeText;
    }

    public static ProblemType fromString(String problemTypeText) {
        return ProblemType.valueOf(problemTypeText.toUpperCase());
    }

    @JsonValue
    public String getText() {
        return problemTypeText;
    }
}
