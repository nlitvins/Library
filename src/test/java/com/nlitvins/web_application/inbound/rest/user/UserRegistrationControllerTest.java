package com.nlitvins.web_application.inbound.rest.user;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.usecase.user.UserRegistrationUseCase;
import com.nlitvins.web_application.inbound.model.UserRequest;
import com.nlitvins.web_application.inbound.model.UserResponse;
import com.nlitvins.web_application.utils.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.nlitvins.web_application.utils.factory.UserTestFactory.givenUser;
import static com.nlitvins.web_application.utils.factory.UserTestFactory.givenUserRequest;
import static com.nlitvins.web_application.utils.factory.UserTestFactory.givenUserResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
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
            doReturn(user).when(userRegistrationUseCase).registerUser(any(User.class));

            UserResponse userResponse = controller.registerUser(userRequest);

            assertThat(userResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenUserResponse());
        }
    }

    @Nested
    class ApiCalls {
        @Test
        void returnRegisteredUser() throws Exception {
            User user = givenUser();
            UserRequest userRequest = givenUserRequest();
            doReturn(user).when(userRegistrationUseCase).registerUser(any(User.class));

            MvcResult mvcResult = mockMvc.perform(
                            post(getControllerURI())
                                    .content(getBodyContent(userRequest))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                    .andReturn();


            UserResponse userResponse = getResponseObject(mvcResult, UserResponse.class);
            assertThat(userResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenUserResponse());

        }
    }
}

