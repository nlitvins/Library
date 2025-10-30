package com.nlitvins.web_application.inbound.rest.reservation;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.repository.JwtRepository;
import com.nlitvins.web_application.domain.usecase.reservation.ReservationReadUseCase;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.utils.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.nlitvins.web_application.utils.factory.ReservationTestFactory.givenReservationResponses;
import static com.nlitvins.web_application.utils.factory.ReservationTestFactory.givenReservations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationReadControllerTest extends AbstractControllerTest {

    private final ReservationReadUseCase reservationReadUseCase = mock();
    private final ReservationReadController controller = new ReservationReadController(reservationReadUseCase);
    private final JwtRepository jwtRepository = mock();

    @Override
    protected String getControllerURI() {
        return "/reservations";
    }

    @Override
    protected Object getController() {
        return controller;
    }

    @BeforeEach
    void setUp() {
        reset(reservationReadUseCase);
    }

    @Nested
    class DirectCalls {
        @Test
        void returnReservationsWhenReservationsCalled() {
            List<Reservation> reservations = givenReservations();
            doReturn(reservations).when(reservationReadUseCase).getReservations();

            List<ReservationResponse> reservationResponses = controller.reservations();

            assertThat(reservationResponses)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .isEqualTo(givenReservationResponses());
        }

        @Test
        void returnReservationsWhenReservationsByToken() {
            List<Reservation> reservations = givenReservations();
            String token = "testToken";
            String authHeader = "Bearer " + token;

            doReturn(reservations).when(reservationReadUseCase).getReservationsByToken(token);

            List<ReservationResponse> reservationResponses = controller.reservationsByToken(authHeader);

            assertThat(reservationResponses)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .isEqualTo(givenReservationResponses());
        }

        @Test
        void returnReservationsWhenReservationsByUserId() {
            List<Reservation> reservations = givenReservations();
            doReturn(reservations).when(reservationReadUseCase).getReservationByUserId(2);

            List<ReservationResponse> reservationResponses = controller.reservationsByUserId(2);

            assertThat(reservationResponses)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .isEqualTo(givenReservationResponses());
        }
    }


    @Nested
    class ApiCalls {
        @Test
        void returnReservationsWhenReservationsCalled() throws Exception {
            List<Reservation> reservations = givenReservations();
            doReturn(reservations).when(reservationReadUseCase).getReservations();

            MvcResult mvcResult = mockMvc.perform(
                            get(getControllerURI()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            List<ReservationResponse> reservationResponses = getResponseList(mvcResult, ReservationResponse.class);

            assertThat(reservationResponses)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .isEqualTo(givenReservationResponses());
        }

        @Test
        void returnReservationsWhenReservationsByToken() throws Exception {
            List<Reservation> reservations = givenReservations();
            String token = "testToken";
            doReturn(reservations).when(reservationReadUseCase).getReservationsByToken(token);

            MvcResult mvcResult = mockMvc.perform(
                            get(getControllerURI() + "/user")
                                    .header("Authorization", "Bearer " + token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            List<ReservationResponse> reservationResponses = getResponseList(mvcResult, ReservationResponse.class);

            assertThat(reservationResponses)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .isEqualTo(givenReservationResponses());
        }

        @Test
        void returnReservationsWhenReservationsByUserId() throws Exception {
            List<Reservation> reservations = givenReservations();
            doReturn(reservations).when(reservationReadUseCase).getReservationByUserId(2);

            MvcResult mvcResult = mockMvc.perform(
                            get(getControllerURI() + "/user" + "/" + 2)
                                    .contentType(getBodyContent(2)))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            List<ReservationResponse> reservationResponses = getResponseList(mvcResult, ReservationResponse.class);

            assertThat(reservationResponses)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .isEqualTo(givenReservationResponses());
        }
    }
}
