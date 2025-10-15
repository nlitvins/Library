package com.nlitvins.web_application.inbound.rest.login;

import com.nlitvins.web_application.domain.usecase.login.UserLoginUseCase;
import com.nlitvins.web_application.inbound.model.LoginRequest;
import com.nlitvins.web_application.inbound.model.LoginResponse;
import com.nlitvins.web_application.inbound.rest.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.nlitvins.web_application.utils.LoginTestFactory.loginRequest;
import static com.nlitvins.web_application.utils.LoginTestFactory.loginUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginControllerTest extends AbstractControllerTest {

    private final UserLoginUseCase userLoginUseCase = mock();
    private final LoginController controller = new LoginController(userLoginUseCase);

    @Override
    protected String getControllerURI() {
        return "/login";
    }

    @Override
    protected Object getController() {
        return controller;
    }

    @BeforeEach
    void setUp() {
        reset(userLoginUseCase);
    }

    @Nested
    class DirectCalls {
        @Test
        void returnUserWhenUserLogin() {
            String testToken = "testToken";
            LoginRequest loginRequest = loginRequest();
            doReturn(testToken).when(userLoginUseCase).loginUser(loginUser());

            LoginResponse loginResponse = controller.loginUser(loginRequest);

            assertEquals(testToken, loginResponse.getToken());

        }
    }

    @Nested
    class ApiCalls {
        @Test
        void returnUserWhenUserLogin() throws Exception {
            String testToken = "testToken";
            LoginRequest loginRequest = loginRequest();
            doReturn(testToken).when(userLoginUseCase).loginUser(loginUser());

            MvcResult mvcResult = mockMvc.perform(
                            post(getControllerURI())
                                    .content(getBodyContent(loginRequest))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                    .andReturn();

            LoginResponse loginResponse = getResponseObject(mvcResult, LoginResponse.class);

            assertEquals(testToken, loginResponse.getToken());
        }
    }


}
