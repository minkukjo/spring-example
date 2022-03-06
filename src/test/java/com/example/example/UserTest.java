package com.example.example;

import com.example.example.presentation.api.request.OrderRequest;
import com.example.example.presentation.api.request.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {
    @Autowired
    private MockMvc mockMvc;

    // 회원가입 테스트
    @BeforeEach
    public void setup() throws Exception {
        final SignupRequest signupRequest = SignupRequest.builder()
                .name("해리")
                .nickname("harry")
                .password("Xptmxmqlqjs1!")
                .email("strike0115@naver.com")
                .gender("man")
                .phoneNumber("1037015889")
                .build();

        final String json = new ObjectMapper().writeValueAsString(signupRequest);
        this.mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Login 테스트")
    public void login() throws Exception {
        String userId = "strike0115@naver.com";
        String password = "Xptmxmqlqjs1!";

        mockMvc.perform(formLogin().user(userId).password(password))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:8080/success"));
    }

    @Test
    @DisplayName("회원가입 제약조건 검증")
    void signup() throws Exception {
        final SignupRequest request = SignupRequest.builder()
                .name("$#$!!")
                .email("str")
                .nickname("AAA")
                .password("xptmxmqlqjs1")
                .phoneNumber("5553432423423423423423")
                .gender("man")
                .build();
        final String json = new ObjectMapper().writeValueAsString(request);
        this.mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.password", is(SignupRequest.class.getDeclaredField("password").getAnnotation(Pattern.class).message())))
                .andExpect(jsonPath("$.errors.phoneNumber", is(SignupRequest.class.getDeclaredField("phoneNumber").getAnnotation(Size.class).message())))
                .andExpect(jsonPath("$.errors.name", is(SignupRequest.class.getDeclaredField("name").getAnnotation(Pattern.class).message())))
                .andExpect(jsonPath("$.errors.nickname", is(SignupRequest.class.getDeclaredField("nickname").getAnnotation(Pattern.class).message())))
                .andExpect(jsonPath("$.errors.email", is(SignupRequest.class.getDeclaredField("email").getAnnotation(Email.class).message())));
    }

    @Test
    @WithMockUser(username = "1", roles = {"USER"})
    @DisplayName("단일 회원 정보 상세 조회")
    void userMe() throws Exception {
        this.mockMvc.perform(get("/api/users/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("해리")))
                .andExpect(jsonPath("$.nickname", is("harry")))
                .andExpect(jsonPath("$.phoneNumber", is(1037015889)))
                .andExpect(jsonPath("$.email", is("strike0115@naver.com")))
                .andExpect(jsonPath("$.gender", is("man")));
    }

    @Test
    @WithMockUser(username = "1", roles = {"USER"})
    @DisplayName("단일 회원 정보 상세 조회")
    void findUser() throws Exception {

        given();

        this.mockMvc.perform(get("/api/users").param("name", "해리"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is("해리")))
                .andExpect(jsonPath("$.content[0].productName", is("삼겹살")));
    }

    private void given() throws Exception {
        final OrderRequest orderRequest = OrderRequest.builder()
                .productName("소고기")
                .build();

        final String orderRequestJson = new ObjectMapper().writeValueAsString(orderRequest);
        this.mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(orderRequestJson))
                .andExpect(status().isOk());

        final OrderRequest orderRequest2 = OrderRequest.builder()
                .productName("삼겹살")
                .build();

        final String orderRequest2Json = new ObjectMapper().writeValueAsString(orderRequest2);
        this.mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(orderRequest2Json))
                .andExpect(status().isOk());
    }

}
