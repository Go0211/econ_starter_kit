package com.econstarterkit.econstarterkit.service;

import com.econstarterkit.econstarterkit.dto.QuizDto;
import com.econstarterkit.econstarterkit.entity.Problem;
import com.econstarterkit.econstarterkit.repository.ProblemRepository;
import com.econstarterkit.econstarterkit.type.Difficulty;
import com.econstarterkit.econstarterkit.type.ProblemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final ProblemRepository problemRepository;

    public List<Problem> getProblem(QuizDto.StrType quizDto) {
        QuizDto.EnumType enumType = StringToEnum(quizDto);

        return problemRepository.findAllByDifficultyAndType(
                enumType.getDifficulty(), enumType.getProblemType()
        );
    }

    private QuizDto.EnumType StringToEnum(QuizDto.StrType quizDto) {
        return QuizDto.EnumType.builder()
                .difficulty(
                        Difficulty.fromString(quizDto.getDifficulty())
                )
                .problemType(
                        ProblemType.fromString(quizDto.getProblemType())
                )
                .build();
    }

    public boolean checkQuizAnswer(Long id, String word) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("에러"));

        return problem.getCorrectWord().equals(word);
    }
}
