package com.witherview.account;

import com.witherview.database.entity.User;
import com.witherview.database.repository.UserRepository;
import com.witherview.support.MockMvcSupporter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends MockMvcSupporter {
    final String email = "hohoho@witherview.com";
    final String password = "123456";
    final String passwordConfirm = "123456";
    final String name = "위더뷰";

    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입_성공케이스() throws Exception {
        AccountDTO.RegisterDTO dto = new AccountDTO.RegisterDTO();
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setPasswordConfirm(passwordConfirm);
        dto.setName(name);

        ResultActions resultActions = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated());

        resultActions.andExpect(jsonPath("$.email").value(email));
        resultActions.andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void 회원가입_실패케이스_이메일_중복() throws Exception {
        userRepository.save(new User(email, password, name));

        AccountDTO.RegisterDTO dto = new AccountDTO.RegisterDTO();
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setPasswordConfirm(passwordConfirm);
        dto.setName(name);

        ResultActions resultActions = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        resultActions.andExpect(jsonPath("$.status").value(400));
    }

    @Test
    public void 회원가입_실패케이스_이메일형태가_아님() throws Exception {
        AccountDTO.RegisterDTO dto = new AccountDTO.RegisterDTO();
        dto.setEmail("iddomain.com");
        dto.setPassword(password);
        dto.setPasswordConfirm(passwordConfirm);
        dto.setName(name);

        ResultActions resultActions = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        resultActions.andExpect(jsonPath("$.message").value("Invalid Input Value"));
        resultActions.andExpect(jsonPath("$.status").value(400));
        resultActions.andExpect(jsonPath("$.errors[0].field").value("email"));
    }

    @Test
    public void 회원가입_실패케이스_비밀번호가_같지_않음() throws Exception {
        AccountDTO.RegisterDTO dto = new AccountDTO.RegisterDTO();
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setPasswordConfirm(passwordConfirm + "1");
        dto.setName(name);

        ResultActions resultActions = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        resultActions.andExpect(jsonPath("$.status").value(400));
    }

    @Test
    public void 회원가입_실패케이스_이름이_짧음() throws Exception {
        AccountDTO.RegisterDTO dto = new AccountDTO.RegisterDTO();
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setPasswordConfirm(passwordConfirm);
        dto.setName("a");

        ResultActions resultActions = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        resultActions.andExpect(jsonPath("$.message").value("Invalid Input Value"));
        resultActions.andExpect(jsonPath("$.status").value(400));
    }
}
