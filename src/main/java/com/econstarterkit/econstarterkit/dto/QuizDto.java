package com.econstarterkit.econstarterkit.dto;

import com.econstarterkit.econstarterkit.type.Difficulty;
import com.econstarterkit.econstarterkit.type.ProblemType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDto {
    ProblemType type;
    Difficulty difficulty;

    public static class GetQuizDtoToStr {
        String type;
        String difficulty;

        public void strToEnum() {
            QuizDto quizDto = new QuizDto();

        }
    }
}
