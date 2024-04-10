package com.econstarterkit.econstarterkit.controller;

import com.econstarterkit.econstarterkit.dto.QuizDto;
import com.econstarterkit.econstarterkit.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/start")
    public ResponseEntity<?> quizStart(
            @RequestBody QuizDto quizDto
    ) {
        quizService.getProblem(quizDto);
        return ResponseEntity.ok().build();
    }
}
