package com.nlitvins.web_application.inbound.rest.user;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.usecase.user.UserSetRoleUseCase;
import com.nlitvins.web_application.inbound.model.UserResponse;
import com.nlitvins.web_application.inbound.rest.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.nlitvins.web_application.utils.UserTestFactory.givenUser;
import static com.nlitvins.web_application.utils.UserTestFactory.givenUserResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO
class UserSetRoleTest extends AbstractControllerTest {

    private final UserSetRoleUseCase userSetRoleUseCase = mock();
    private final UserSetRoleController controller = new UserSetRoleController(userSetRoleUseCase);

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
        reset(userSetRoleUseCase);
    }

    @Nested
    class DirectCalls {
        @Test
        void returnUserSetRole() {
            User user = givenUser();

            doReturn(user).when(userSetRoleUseCase).setUserRole(user.getId());
            UserResponse userResponse = controller.setUserRole(user.getId());

            assertThat(userResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenUserResponse());
        }

        @Test
        void returnUserSetRoleLibrarian() {
            User user = givenUser();

            doReturn(user).when(userSetRoleUseCase).setLibrarianRole(user.getId());
            UserResponse userResponse = controller.setLibrarianRole(user.getId());

            assertThat(userResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenUserResponse());
        }
    }

    @Nested
    class ApiCalls {
        @Test
        void returnUserSetRole() throws Exception {
            User user = givenUser();
            doReturn(user).when(userSetRoleUseCase).setUserRole(user.getId());

            MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.put(getControllerURI() + "/" + 2 + "/role/user"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            UserResponse userResponse = getResponseObject(mvcResult, UserResponse.class);


            assertThat(userResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenUserResponse());
        }

        @Test
        void returnUserSetRoleLibrarian() throws Exception {
            User user = givenUser();
            doReturn(user).when(userSetRoleUseCase).setLibrarianRole(user.getId());


            MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.put(getControllerURI() + "/" + 2 + "/role/librarian"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            UserResponse userResponse = getResponseObject(mvcResult, UserResponse.class);

            assertThat(userResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenUserResponse());
        }
    }
}
