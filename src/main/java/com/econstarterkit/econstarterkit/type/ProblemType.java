package com.econstarterkit.econstarterkit.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProblemType {
    WORD("WORD"),
    HISTORY("HISTORY"),
    OTHER("OTHER"),
    ALL("ALL");

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
