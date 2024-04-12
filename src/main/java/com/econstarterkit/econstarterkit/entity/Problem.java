package com.econstarterkit.econstarterkit.entity;

import com.econstarterkit.econstarterkit.dto.ProblemDto;
import com.econstarterkit.econstarterkit.type.Difficulty;
import com.econstarterkit.econstarterkit.type.Institution;
import com.econstarterkit.econstarterkit.type.ProblemType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "problem")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    ProblemType type;

    @Column(name = "description", length = 4000)
    String description;

    @Column(name = "correct_word")
    String correctWord;

    @Column(name = "other_correct_word")
    String otherCorrectWord;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "institution")
    Institution institution;

    public Problem toEntity(ProblemDto problemDto) {
        return Problem.builder()
                .id(problemDto.getId())
                .type(problemDto.getType())
                .description(problemDto.getDescription())
                .correctWord(problemDto.getCorrectWord())
                .otherCorrectWord(problemDto.getOtherCorrectWord())
                .difficulty(problemDto.getDifficulty())
                .institution(problemDto.getInstitution())
                .build();
    }
}
