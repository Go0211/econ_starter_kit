package com.econstarterkit.econstarterkit.service;

import com.econstarterkit.econstarterkit.dto.QuizDto;
import com.econstarterkit.econstarterkit.entity.Problem;
import com.econstarterkit.econstarterkit.repository.ProblemRepository;
import com.econstarterkit.econstarterkit.type.Difficulty;
import com.econstarterkit.econstarterkit.type.ProblemType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final ProblemRepository problemRepository;
    private final RedisTemplate<String, Object> redisTemplate;

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

    public List<Problem> getProblemListAll() {
        return problemRepository.findAll();
    }

    public void setUserProblemList(Long userId) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Map<String, Object> map = new HashMap<>();

        map.put("problemList", getProblemListAll());
        valueOperations.set(userIdToStr(userId), map);
    }

    private String userIdToStr(Long userId) {
        return String.valueOf(userId);
    }

    public List<Problem> getUserProblemList(Long userId) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Map<String, Object> stringObjectMap =
                (Map<String, Object>) valueOperations.get(userIdToStr(userId));
        return (List<Problem>) stringObjectMap.get("problemList");
    }

    public void updateUserProblemList(Long userId, List<Problem> problemList) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(
                userIdToStr(userId),
                new HashMap<>().put("problemList", problemList)
        );
    }
}
