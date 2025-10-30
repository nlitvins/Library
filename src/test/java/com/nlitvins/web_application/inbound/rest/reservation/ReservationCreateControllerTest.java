package com.nlitvins.web_application.inbound.rest.reservation;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.usecase.reservation.ReservationCreateUseCase;
import com.nlitvins.web_application.inbound.model.ReservationCreateRequest;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.utils.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.nlitvins.web_application.utils.factory.ReservationTestFactory.givenReservation;
import static com.nlitvins.web_application.utils.factory.ReservationTestFactory.givenReservationCreateRequest;
import static com.nlitvins.web_application.utils.factory.ReservationTestFactory.givenReservationResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationCreateControllerTest extends AbstractControllerTest {

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

    @Nested
    class DirectCalls {
        @Test
        void returnReservationWhenReservationCreated() {
            ReservationCreateRequest reservationCreateRequest = givenReservationCreateRequest();
            Reservation reservation = givenReservation();
            doReturn(reservation).when(reservationCreateUseCase).registerReservation(newReservation());

            ReservationResponse reservationResponse = controller.reserveBook(reservationCreateRequest);

            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenReservationResponse());
        }

    }

    @Nested
    class ApiCalls {
        @Test
        void returnReservationWhenReservationCreated() throws Exception {
            ReservationCreateRequest reservationCreateRequest = givenReservationCreateRequest();
            Reservation reservation = givenReservation();

            doReturn(reservation).when(reservationCreateUseCase).registerReservation(newReservation());

            MvcResult mvcResult = mockMvc.perform(
                            post(getControllerURI())
                                    .content(getBodyContent(reservationCreateRequest))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            ReservationResponse reservationResponse = getResponseObject(mvcResult, ReservationResponse.class);
            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenReservationResponse());
        }
    }

    public Reservation newReservation() {
        return Reservation.builder()
                .userId(givenReservationCreateRequest().getUserId())
                .bookId(givenReservationCreateRequest().getBookId())
                .status(ReservationStatus.NEW)
                .extensionCount((short) 0)
                .build();
    }
}

