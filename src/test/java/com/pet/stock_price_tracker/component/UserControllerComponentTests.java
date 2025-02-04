package com.pet.stock_price_tracker.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.user.login.UserResponseLoginDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("component-test")
@AutoConfigureMockMvc
public class UserControllerComponentTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void user_CreateUserWithValidData_ReturnsValidResponseEntity() throws Exception {
        //given
        UserDTO userDTO = new UserDTO(
                "createuserlogin",
                "createuserpass"
        );
        String json = objectMapper.writeValueAsString(userDTO);
        var builder = MockMvcRequestBuilders
                .post(Routes.API_USER_REGISTRATION_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        //when
        MvcResult result = mockMvc.perform(builder)
                //then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        UserResponseDTO createdUser = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDTO.class
        );

        Assertions.assertEquals(userDTO.getLogin(), createdUser.getLogin());
    }

    @Test
    @WithMockUser
    public void user_LoginWithValidData_ReturnsValidResponseEntity() throws Exception {
        //given
        UserDTO userDTO = new UserDTO(
                "user",
                "userpass"
        );
        var userCreationRequest = MockMvcRequestBuilders
                .post(Routes.API_USER_REGISTRATION_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO));

        String createdUser = mockMvc.perform(userCreationRequest)
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserResponseDTO user = objectMapper.readValue(createdUser, UserResponseDTO.class);

        String json = objectMapper.writeValueAsString(userDTO);
        var builder = MockMvcRequestBuilders
                .post(Routes.API_USER_LOGIN_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        //when
        MvcResult result = mockMvc.perform(builder)
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.userInfo").exists(),
                        jsonPath("$.userInfo.id").exists(),
                        jsonPath("$.userInfo.login").exists(),
                        jsonPath("$.userInfo.id").isNotEmpty(),
                        jsonPath("$.userInfo.login").isNotEmpty(),
                        jsonPath("$.authentication").exists(),
                        jsonPath("$.authentication.accessToken").exists(),
                        jsonPath("$.authentication.refreshToken").exists(),
                        jsonPath("$.authentication.accessToken").isNotEmpty(),
                        jsonPath("$.authentication.refreshToken").isNotEmpty()
                )
                .andReturn();

        UserResponseLoginDTO loginedUser = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseLoginDTO.class
        );

        Assertions.assertEquals(
                user.getLogin(),
                loginedUser.getUserInfo().getLogin()
        );
    }

}
