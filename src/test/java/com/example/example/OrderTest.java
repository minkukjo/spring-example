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

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderTest {
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
    @WithMockUser(username = "1", roles = {"USER"})
    @DisplayName("단일 회원 주문 및 목록 조회")
    void orders() throws Exception {

        final OrderRequest orderRequest = OrderRequest.builder()
                .productName("소고기")
                .build();

        final String orderRequestJson = new ObjectMapper().writeValueAsString(orderRequest);
        this.mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(orderRequestJson))
                .andDo(print())
                .andExpect(status().isOk());

        final OrderRequest orderRequest2 = OrderRequest.builder()
                .productName("삼겹살")
                .build();

        final String orderRequest2Json = new ObjectMapper().writeValueAsString(orderRequest2);
        this.mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(orderRequest2Json))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }
}
