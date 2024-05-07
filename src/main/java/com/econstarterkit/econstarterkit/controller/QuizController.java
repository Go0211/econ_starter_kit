package com.econstarterkit.econstarterkit.controller;

import com.econstarterkit.econstarterkit.dto.QuizDto;
import com.econstarterkit.econstarterkit.entity.Problem;
import com.econstarterkit.econstarterkit.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //    해당 유저의 문제들을 세팅함
    @GetMapping("/set/{userId}")
    public ResponseEntity<?> setProblemList(@PathVariable("userId") Long userId) {
        quizService.setUserProblemList(userId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //    해당 유저의 문제들을 가져옴
    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getProblemList(@PathVariable("userId") Long userId) {
        List<Problem> problemList = quizService.getUserProblemList(userId);
        return ResponseEntity.ok(problemList);
    }

    //    user가 맞췄을 때 그 해당 문제의 번호를 db에서 삭제
    @GetMapping("remove/{userId}/{problemNum}")
    public ResponseEntity<?> deleteProblem(@PathVariable("userId") Long userId,
                                                       @PathVariable("problemNum") int problemNum) {
        List<Problem> problemList = quizService.getUserProblemList(userId);
        problemList.remove(problemNum);
        quizService.updateUserProblemList(userId, problemList);

        return ResponseEntity.ok(problemList);
    }

    //  끝나고 해당 list를 저장
    @GetMapping("finish/{userId}")
    public ResponseEntity<?> finishProblemListFromUserId(@PathVariable("userId") Long userId) {
        quizService.setUserProblemList(userId);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
