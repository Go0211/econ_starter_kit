package com.econstarterkit.econstarterkit.controller;

import com.econstarterkit.econstarterkit.dto.QuizDto;
import com.econstarterkit.econstarterkit.entity.Problem;
import com.econstarterkit.econstarterkit.service.QuizService;
import com.econstarterkit.econstarterkit.type.Difficulty;
import com.econstarterkit.econstarterkit.type.Institution;
import com.econstarterkit.econstarterkit.type.ProblemType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
class QuizControllerTest {
    @MockBean
    private QuizService quizService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("퀴즈 시작 성공")
    void quizStartSuccess() throws Exception {
        // given
        List<Problem> problemList = Arrays.asList(
                Problem.builder()
                        .id(1L)
                        .type(ProblemType.ALL)
                        .description("test")
                        .correctWord("t")
                        .otherCorrectWord("est")
                        .difficulty(Difficulty.ALL)
                        .institution(Institution.AUTHOR_INDICATION_X)
                        .build(),
                Problem.builder()
                        .id(2L)
                        .type(ProblemType.ALL)
                        .description("test")
                        .correctWord("t")
                        .otherCorrectWord("est")
                        .difficulty(Difficulty.ALL)
                        .institution(Institution.AUTHOR_INDICATION_X)
                        .build()
        );
        given(quizService.getProblem(any(QuizDto.StrType.class)))
                .willReturn(problemList);
        // when
        // then
        mockMvc.perform(post("/quiz/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new QuizDto.StrType("all", "all")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].type").value("all"))
                .andExpect(jsonPath("$[0].description").value("test"))
                .andExpect(jsonPath("$[0].correctWord").value("t"))
                .andExpect(jsonPath("$[0].otherCorrectWord").value("est"))
                .andExpect(jsonPath("$[0].difficulty").value("all"))
                .andExpect(jsonPath("$[0].institution").value("AUTHOR_INDICATION_X"));
    }

    @Test
    @DisplayName("퀴즈 정답 맞출 시 true출력")
    void quizAnswerCorrect() throws Exception {
        // given
        given(quizService.checkQuizAnswer(1L, "test"))
                .willReturn(true);
        // when
        // then
        mockMvc.perform(post("/quiz/correct-check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new QuizDto.ProblemData(1L, "test")
                )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName("퀴즈 정답 못맞출 시 false출력")
    void quizAnswerFail() throws Exception {
        // given
        given(quizService.checkQuizAnswer(1L, "test"))
                .willReturn(true);
        // when
        // then
        mockMvc.perform(post("/quiz/correct-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new QuizDto.ProblemData(1L, "tet")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }
}