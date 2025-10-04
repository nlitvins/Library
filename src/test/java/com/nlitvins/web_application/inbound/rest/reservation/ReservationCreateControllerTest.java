package com.nlitvins.web_application.inbound.rest.reservation;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.usecase.reservation.ReservationCreateUseCase;
import com.nlitvins.web_application.inbound.model.ReservationCreateRequest;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.rest.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.nlitvins.web_application.utils.ReservationTestFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationCreateControllerTest extends AbstractControllerTest {
    @Nested
    class DirectCalls {
        @Test
        void returnReservationWhenReservationCreated() {
            ReservationCreateRequest reservationCreateRequest = givenReservationCreateRequest();
            Reservation reservation = givenReservation();
            doReturn(reservation).when(reservationCreateUseCase).registerReservation(any(Reservation.class));

            ReservationResponse reservationResponse = controller.reserveBook(reservationCreateRequest);

            assertEquals(givenReservationResponse().getId(), reservationResponse.getId());
        }
    }

    @Nested
    class ApiCalls {
        @Test
        void returnReservationWhenReservationCreated() throws Exception {
            ReservationCreateRequest reservationCreateRequest = givenReservationCreateRequest();
            Reservation reservation = givenReservation();
            doReturn(reservation).when(reservationCreateUseCase).registerReservation(any(Reservation.class));

            MvcResult mvcResult = mockMvc.perform(
                            post(getControllerURI())
                                    .content(getBodyContent(reservationCreateRequest))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            ReservationResponse reservationResponse = getResponseObject(mvcResult, ReservationResponse.class);
            assertEquals(givenReservationResponse().getId(), reservationResponse.getId());
        }
    }

    private final ReservationCreateUseCase reservationCreateUseCase = mock();
    private final ReservationCreateController controller = new ReservationCreateController(reservationCreateUseCase);

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
        reset(reservationCreateUseCase);
    }
}

