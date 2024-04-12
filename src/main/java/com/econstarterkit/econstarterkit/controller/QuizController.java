package com.econstarterkit.econstarterkit.controller;

import com.econstarterkit.econstarterkit.dto.QuizDto;
import com.econstarterkit.econstarterkit.entity.Problem;
import com.econstarterkit.econstarterkit.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("/main")
    public ResponseEntity<?> quizMain() {
//        메인쪽 전체적인 data전송

        return ResponseEntity.ok().build();
    }

    @PostMapping("/start")
    public ResponseEntity<?> quizStart(
            @RequestBody QuizDto.StrType quizDto
    ) {
//      난의도, 타입에 따라서 문제 다 가져오기
        List<Problem> problem = quizService.getProblem(quizDto);
        return ResponseEntity.ok(problem);
    }

    @PostMapping("/correct-check")
    public ResponseEntity<?> correctCheck(
            @RequestBody QuizDto.ProblemData problemData
    ) {
        boolean correct = quizService.checkQuizAnswer(
                problemData.getProblemId(), problemData.getWord());
        return ResponseEntity.ok(correct);
    }
}
