package com.econstarterkit.econstarterkit.dto;

import com.econstarterkit.econstarterkit.type.Difficulty;
import com.econstarterkit.econstarterkit.type.ProblemType;
import lombok.*;

public class QuizDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrType {
        String problemType;
        String difficulty;
    }

    @Getter
    @Setter
    @Builder
    public static class EnumType {
        ProblemType problemType;
        Difficulty difficulty;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProblemData {
        Long problemId;
        String word;
    }
}
