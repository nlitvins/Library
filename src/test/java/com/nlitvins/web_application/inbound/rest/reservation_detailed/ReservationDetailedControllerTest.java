package com.nlitvins.web_application.inbound.rest.reservation_detailed;

import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.domain.usecase.reservation_detailed.ReservationDetailedUseCase;
import com.nlitvins.web_application.inbound.model.ReservationDetailedResponse;
import com.nlitvins.web_application.utils.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.nlitvins.web_application.utils.factory.ReservationDetailedTestFactory.givenReservationDetailedList;
import static com.nlitvins.web_application.utils.factory.ReservationDetailedTestFactory.givenReservationDetailedResponseList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationDetailedControllerTest extends AbstractControllerTest {

    private final ReservationDetailedUseCase reservationDetailedUseCase = mock();
    private final ReservationDetailedController controller = new ReservationDetailedController(reservationDetailedUseCase);

    @Override
    protected String getControllerURI() {
        return "/reservations/detailed";
    }

    @Override
    protected Object getController() {
        return controller;
    }

    @BeforeEach
    void setUp() {
        reset(reservationDetailedUseCase);
    }

    @Nested
    class DirectCalls {
        @Test
        void returnAllWhenFindAll() {
            List<ReservationDetailed> reservationDetailedList = givenReservationDetailedList();
            doReturn(reservationDetailedList).when(reservationDetailedUseCase).getAll();

            List<ReservationDetailedResponse> reservationDetailedResponses = controller.findAll();

            assertThat(reservationDetailedResponses)
                    .usingRecursiveComparison()
                    .isEqualTo(givenReservationDetailedResponseList());

        }

        @Test
        void returnAllByToken() {
            List<ReservationDetailed> reservationDetailedList = givenReservationDetailedList();
            String token = "token";
            String authHeader = "Bearer " + token;

            doReturn(reservationDetailedList).when(reservationDetailedUseCase).getReservationsByToken(token);

            List<ReservationDetailedResponse> reservationDetailedResponses = controller.reservationDetailedByToken(authHeader);

            assertThat(reservationDetailedResponses)
                    .usingRecursiveComparison()
                    .isEqualTo(givenReservationDetailedResponseList());
        }

        @Test
        void returnAllByUserByUserId() {
            List<ReservationDetailed> reservationDetailedList = givenReservationDetailedList();
            int userId = reservationDetailedList.getFirst().getUser().getId();

            doReturn(reservationDetailedList).when(reservationDetailedUseCase).getByUserId(userId);

            List<ReservationDetailedResponse> reservationDetailedResponses = controller.findByUserId(userId);

            assertThat(reservationDetailedResponses)
                    .usingRecursiveComparison()
                    .isEqualTo(givenReservationDetailedResponseList());
        }
    }

    @Nested
    class ApiCalls {
        @Test
        void returnAllWhenFindAll() throws Exception {
            List<ReservationDetailed> reservationDetailedList = givenReservationDetailedList();

            doReturn(reservationDetailedList).when(reservationDetailedUseCase).getAll();

            MvcResult mvcResult = mockMvc.perform(
                            get(getControllerURI()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            List<ReservationDetailedResponse> reservationDetailedResponses = getResponseList(mvcResult, ReservationDetailedResponse.class);


            assertThat(reservationDetailedResponses)
                    .usingRecursiveComparison()
                    .isEqualTo(givenReservationDetailedResponseList());

        }

        @Test
        void returnAllByToken() throws Exception {
            List<ReservationDetailed> reservationDetailedList = givenReservationDetailedList();
            String token = "testToken";

            doReturn(reservationDetailedList).when(reservationDetailedUseCase).getReservationsByToken(token);

            MvcResult mvcResult = mockMvc.perform(
                            get(getControllerURI() + "/user")
                                    .header("Authorization", "Bearer " + token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();


            List<ReservationDetailedResponse> reservationDetailedResponses = getResponseList(mvcResult, ReservationDetailedResponse.class);

            assertThat(reservationDetailedResponses)
                    .usingRecursiveComparison()
                    .isEqualTo(givenReservationDetailedResponseList());
        }

        @Test
        void returnAllByUserByUserId() throws Exception {
            List<ReservationDetailed> reservationDetailedList = givenReservationDetailedList();
            int userId = reservationDetailedList.getFirst().getUser().getId();

            doReturn(reservationDetailedList).when(reservationDetailedUseCase).getByUserId(userId);

            MvcResult mvcResult = mockMvc.perform(
                            get(getControllerURI() + "/user" + "/" + userId)
                                    //TODO
                                    .contentType(getBodyContent(2)))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            List<ReservationDetailedResponse> reservationDetailedResponses = getResponseList(mvcResult, ReservationDetailedResponse.class);

            assertThat(reservationDetailedResponses)
                    .usingRecursiveComparison()
                    .isEqualTo(givenReservationDetailedResponseList());
        }
    }
}


