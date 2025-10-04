package com.nlitvins.web_application.inbound.rest.reservation;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.usecase.reservation.ReservationReadUseCase;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.rest.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.nlitvins.web_application.utils.ReservationTestFactory.givenReservationResponses;
import static com.nlitvins.web_application.utils.ReservationTestFactory.givenReservations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ReservationReadControllerTest extends AbstractControllerTest {
    @Nested
    class DirectCalls {
        @Test
        void returnReservationsWhenReservationsIsCalled() {
            List<Reservation> reservations = givenReservations();
            doReturn(reservations).when(reservationReadUseCase).getReservations();

            List<ReservationResponse> reservationResponses = controller.reservations();

            assertNotNull(reservationResponses);
            assertEquals(2, reservationResponses.size());
            assertEquals(givenReservationResponses(), reservationResponses);
        }
    }

    @Nested
    class ApiCalls {

    }


    private final ReservationReadUseCase reservationReadUseCase = mock();
    private final ReservationReadController controller = new ReservationReadController(reservationReadUseCase);

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
}
