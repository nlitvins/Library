package com.nlitvins.web_application.inbound.rest.user;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.usecase.user.UserRegistrationUseCase;
import com.nlitvins.web_application.inbound.model.UserRequest;
import com.nlitvins.web_application.inbound.model.UserResponse;
import com.nlitvins.web_application.inbound.rest.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.nlitvins.web_application.utils.UserTestFactory.givenUser;
import static com.nlitvins.web_application.utils.UserTestFactory.givenUserRequest;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRegistrationControllerTest extends AbstractControllerTest {

    private final UserRegistrationUseCase userRegistrationUseCase = mock();
    private final UserRegistrationController controller = new UserRegistrationController(userRegistrationUseCase);

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
        reset(userRegistrationUseCase);
    }

    @Nested
    class DirectCalls {
        @Test
        void returnRegisteredUser() {
            User user = givenUser();
            UserRequest userRequest = givenUserRequest();
            doReturn(user).when(userRegistrationUseCase).registerUser(user);

            UserResponse userResponse = controller.registerUser(userRequest);
//TODO
            assertNull(userResponse);
        }
    }

    @Nested
    class ApiCalls {
        @Test
        void returnRegisteredUser() throws Exception {
            User user = givenUser();
            UserRequest userRequest = givenUserRequest();
            doReturn(user).when(userRegistrationUseCase).registerUser(user);

            MvcResult mvcResult = mockMvc.perform(
                            post(getControllerURI())
                                    .content(getBodyContent(userRequest))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                    .andReturn();


            UserResponse userResponse = getResponseObject(mvcResult, UserResponse.class);
//TODO
            assertNull(userResponse);

        }
    }
}

