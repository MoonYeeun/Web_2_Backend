package com.witherview.selfPractice;

import com.witherview.selfPractice.Question.SelfQuestionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SelfQuestionApiTest extends SelfPracticeSupporter {

    @Test
    public void 질문_등록성공() throws Exception {
        SelfQuestionDTO.QuestionDTO questionDTO = SelfQuestionDTO.QuestionDTO.builder()
                .question(question)
                .answer(answer)
                .order(order)
                .build();

        List<SelfQuestionDTO.QuestionDTO> list = new ArrayList<>();
        list.add(questionDTO);

        SelfQuestionDTO.QuestionSaveDTO requestDTO = new SelfQuestionDTO.QuestionSaveDTO();
        requestDTO.setListId(listId);
        requestDTO.setQuestions(list);

        ResultActions resultActions = mockMvc.perform(post("/self/question")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        resultActions.andExpect(jsonPath("$[0].question").value(question));
        resultActions.andExpect(jsonPath("$[0].answer").value(answer));
        resultActions.andExpect(jsonPath("$[0].order").value(order));
    }

    @Test
    public void 질문_수정성공() throws Exception {
        SelfQuestionDTO.QuestionUpdateDTO dto = SelfQuestionDTO.QuestionUpdateDTO.builder()
                .id(questionId)
                .question(updatedQuestion)
                .answer(updatedAnswer)
                .order(order)
                .build();

        List<SelfQuestionDTO.QuestionUpdateDTO> list = new ArrayList<>();
        list.add(dto);

        ResultActions resultActions = mockMvc.perform(patch("/self/question")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(list)))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$[0].question").value(updatedQuestion));
        resultActions.andExpect(jsonPath("$[0].answer").value(updatedAnswer));
    }

    @Test
    public void 질문_등록실패_입력값_공백() throws Exception {
        SelfQuestionDTO.QuestionDTO questionDTO = SelfQuestionDTO.QuestionDTO.builder()
                .question("")
                .answer(answer)
                .order(order)
                .build();

        List<SelfQuestionDTO.QuestionDTO> list = new ArrayList<>();
        list.add(questionDTO);

        SelfQuestionDTO.QuestionSaveDTO requestDTO = new SelfQuestionDTO.QuestionSaveDTO();
        requestDTO.setListId(listId);
        requestDTO.setQuestions(list);

        ResultActions resultActions = mockMvc.perform(post("/self/question")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        resultActions.andExpect(jsonPath("$.message").value("Invalid Input Value"));
        resultActions.andExpect(jsonPath("$.status").value(400));
    }

    @Test
    public void 질문_수정실패_입력값_공백() throws Exception {
        SelfQuestionDTO.QuestionUpdateDTO dto = SelfQuestionDTO.QuestionUpdateDTO.builder()
                .id(questionId)
                .question("")
                .answer(updatedAnswer)
                .order(order)
                .build();

        List<SelfQuestionDTO.QuestionUpdateDTO> list = new ArrayList<>();
        list.add(dto);

        ResultActions resultActions = mockMvc.perform(patch("/self/question")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(list)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        resultActions.andExpect(jsonPath("$.message").value("Invalid Input Value"));
        resultActions.andExpect(jsonPath("$.status").value(400));
    }

    @Test
    public void 질문_등록실패_없는_질문리스트() throws Exception {
        SelfQuestionDTO.QuestionDTO questionDTO = SelfQuestionDTO.QuestionDTO.builder()
                .question(question)
                .answer(answer)
                .order(order)
                .build();

        List<SelfQuestionDTO.QuestionDTO> list = new ArrayList<>();
        list.add(questionDTO);

        SelfQuestionDTO.QuestionSaveDTO requestDTO = new SelfQuestionDTO.QuestionSaveDTO();
        requestDTO.setListId(listId);
        requestDTO.setQuestions(list);

        ResultActions resultActions = mockMvc.perform(post("/self/question")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());

        resultActions.andExpect(jsonPath("$.message").value("해당 질문리스트가 없습니다."));
        resultActions.andExpect(jsonPath("$.status").value(404));
    }

    @Test
    public void 질문_수정실패_없는_질문() throws Exception {
        SelfQuestionDTO.QuestionUpdateDTO dto = SelfQuestionDTO.QuestionUpdateDTO.builder()
                .id(questionId)
                .question(updatedQuestion)
                .answer(updatedAnswer)
                .order(order)
                .build();

        List<SelfQuestionDTO.QuestionUpdateDTO> list = new ArrayList<>();
        list.add(dto);

        ResultActions resultActions = mockMvc.perform(patch("/self/question")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(list)))
                .andDo(print())
                .andExpect(status().isNotFound());

        resultActions.andExpect(jsonPath("$.message").value("해당 질문이 없습니다."));
        resultActions.andExpect(jsonPath("$.status").value(404));
    }
}
