package com.econstarterkit.econstarterkit.dto;

import com.econstarterkit.econstarterkit.entity.Problem;
import com.econstarterkit.econstarterkit.type.Difficulty;
import com.econstarterkit.econstarterkit.type.Institution;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemDto {
    Long id;

    String description;

    String correctWord;

    Difficulty difficulty;

    Institution institution;

    public ProblemDto toDto(Problem problem) {
        return ProblemDto.builder()
                .id(problem.getId())
                .description(problem.getDescription())
                .correctWord(problem.getCorrectWord())
                .difficulty(problem.getDifficulty())
                .institution(problem.getInstitution())
                .build();
    }
}
