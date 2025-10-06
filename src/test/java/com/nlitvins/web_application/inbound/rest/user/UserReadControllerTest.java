package com.nlitvins.web_application.inbound.rest.user;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.usecase.user.UserReadUseCase;
import com.nlitvins.web_application.inbound.model.UserResponse;
import com.nlitvins.web_application.inbound.rest.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.nlitvins.web_application.utils.UserTestFactory.givenUsers;
import static com.nlitvins.web_application.utils.UserTestFactory.givenUsersResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserReadControllerTest extends AbstractControllerTest {

    private final UserReadUseCase userReadUseCase = mock();
    private final UserReadController controller = new UserReadController(userReadUseCase);

    @Override
    protected String getControllerURI() {
        return "/users";
    }

    @Override
    protected Object getController() {
        return controller;
    }

    @BeforeEach
    void setUp() {
        reset(userReadUseCase);
    }

    @Nested
    class DirectCalls {
        @Test
        void returnUsers() {
            List<User> users = givenUsers();
            doReturn(users).when(userReadUseCase).getUsers();

            List<UserResponse> userResponses = controller.getUsers();


            assertNotNull(userResponses);
            assertEquals(2, userResponses.size());
            assertEquals(givenUsersResponse(), userResponses);
        }
    }

    @Nested
    class ApiCalls {
        @Test
        void returnUsers() throws Exception {
            List<User> users = givenUsers();
            doReturn(users).when(userReadUseCase).getUsers();

            MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.get(getControllerURI())
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            List<UserResponse> userResponses = getResponseList(mvcResult, UserResponse.class);
            assertEquals(givenUsersResponse(), userResponses);
        }
    }

}
