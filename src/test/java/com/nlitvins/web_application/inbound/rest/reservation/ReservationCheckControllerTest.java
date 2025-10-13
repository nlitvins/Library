package com.nlitvins.web_application.inbound.rest.reservation;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.usecase.reservation.ReservationCheckUseCase;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.rest.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.nlitvins.web_application.utils.ReservationTestFactory.givenReservation;
import static com.nlitvins.web_application.utils.ReservationTestFactory.givenReservationResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationCheckControllerTest extends AbstractControllerTest {

    private final ReservationCheckUseCase reservationCheckUseCase = mock();
    private final ReservationCheckController controller = new ReservationCheckController(reservationCheckUseCase);

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
        reset(reservationCheckUseCase);
    }

    @Nested
    class DirectCalls {

        @Test
        void returnReservationWhenReceiveReservation() {
            int id = 1;
            Reservation reservation = givenReservation(id);
            ReservationResponse givenReservationResponse = givenReservationResponse(id);
            doReturn(reservation).when(reservationCheckUseCase)
                    .receiveBook(reservation.getId());

            ReservationResponse reservationResponse = controller.receiveBook(reservation.getId());
            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("createdDate", "termDate", "updatedDate")
                    .isEqualTo(givenReservationResponse);

        }

        @Test
        void returnReservationWhenExtendReservation() {
            int id = 1;
            Reservation reservation = givenReservation(id);
            ReservationResponse givenReservationResponse = givenReservationResponse(id);
            doReturn(reservation).when(reservationCheckUseCase)
                    .extendBook(reservation.getId());

            ReservationResponse reservationResponse = controller.extentBook(reservation.getId());

            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("createdDate", "termDate", "updatedDate")
                    .isEqualTo(givenReservationResponse);
        }

        @Test
        void returnReservationWhenCompleteReservation() {
            int id = 1;
            Reservation reservation = givenReservation(id);
            ReservationResponse givenReservationResponse = givenReservationResponse(id);
            doReturn(reservation).when(reservationCheckUseCase)
                    .completeReservation(reservation.getId());

            ReservationResponse reservationResponse = controller.completeReservation(reservation.getId());

            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("createdDate", "termDate", "updatedDate")
                    .isEqualTo(givenReservationResponse);
        }


        @Test
        void returnReservationWhenCancelReservation() {
            int id = 1;
            Reservation reservation = givenReservation(id);
            ReservationResponse givenReservationResponse = givenReservationResponse(id);
            doReturn(reservation).when(reservationCheckUseCase)
                    .cancelReservation(reservation.getId());

            ReservationResponse reservationResponse = controller.cancelReservation(reservation.getId());

            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("createdDate", "termDate", "updatedDate")
                    .isEqualTo(givenReservationResponse);
        }

        @Test
        void returnReservationWhenLoseBook() {
            int id = 1;
            Reservation reservation = givenReservation(id);
            ReservationResponse givenReservationResponse = givenReservationResponse(id);
            doReturn(reservation).when(reservationCheckUseCase)
                    .loseBook(reservation.getId());

            ReservationResponse reservationResponse = controller.loseBook(reservation.getId());

            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("createdDate", "termDate", "updatedDate")
                    .isEqualTo(givenReservationResponse);
        }

    }

    @Nested
    class ApiCalls {

        @Test
        void returnReservationWhenReservationReceived() throws Exception {
            int id = 1;
            Reservation reservation = givenReservation(id);
            ReservationResponse givenReservationResponse = givenReservationResponse(id);
            doReturn(reservation).when(reservationCheckUseCase)
                    .receiveBook(reservation.getId());

            MvcResult mvcResult = mockMvc.perform(
                            put(getControllerURI() + "/" + reservation.getId() + "/receiving")
                                    .content(getBodyContent(reservation.getId())))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            ReservationResponse reservationResponse = getResponseObject(mvcResult, ReservationResponse.class);
            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("createdDate", "termDate", "updatedDate")
                    .isEqualTo(givenReservationResponse);
        }

        @Test
        void returnReservationWhenExtendReservation() throws Exception {
            int id = 1;
            Reservation reservation = givenReservation(id);
            ReservationResponse givenReservationResponse = givenReservationResponse(id);
            doReturn(reservation).when(reservationCheckUseCase)
                    .extendBook(reservation.getId());

            MvcResult mvcResult = mockMvc.perform(
                            put(getControllerURI() + "/" + reservation.getId() + "/extension")
                                    .content(getBodyContent(reservation.getId())))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            ReservationResponse reservationResponse = getResponseObject(mvcResult, ReservationResponse.class);
            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("createdDate", "termDate", "updatedDate")
                    .isEqualTo(givenReservationResponse);
        }

        @Test
        void returnReservationWhenCompleteReservation() throws Exception {
            int id = 1;
            Reservation reservation = givenReservation(id);
            ReservationResponse givenReservationResponse = givenReservationResponse(id);
            doReturn(reservation).when(reservationCheckUseCase)
                    .completeReservation(reservation.getId());

            MvcResult mvcResult = mockMvc.perform(
                            put(getControllerURI() + "/" + reservation.getId() + "/completed")
                                    .content(getBodyContent(reservation.getId())))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            ReservationResponse reservationResponse = getResponseObject(mvcResult, ReservationResponse.class);
            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("createdDate", "termDate", "updatedDate")
                    .isEqualTo(givenReservationResponse);
        }

        @Test
        void returnReservationWhenCancelReservation() throws Exception {
            int id = 1;
            Reservation reservation = givenReservation(id);
            ReservationResponse givenReservationResponse = givenReservationResponse(id);
            doReturn(reservation).when(reservationCheckUseCase)
                    .cancelReservation(reservation.getId());

            MvcResult mvcResult = mockMvc.perform(
                            put(getControllerURI() + "/" + reservation.getId() + "/cancel")
                                    .content(getBodyContent(reservation.getId())))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            ReservationResponse reservationResponse = getResponseObject(mvcResult, ReservationResponse.class);
            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("createdDate", "termDate", "updatedDate")
                    .isEqualTo(givenReservationResponse);
        }

        @Test
        void returnReservationWhenLoseBook() throws Exception {
            int id = 1;
            Reservation reservation = givenReservation(id);
            ReservationResponse givenReservationResponse = givenReservationResponse(id);
            doReturn(reservation).when(reservationCheckUseCase)
                    .loseBook(reservation.getId());

            MvcResult mvcResult = mockMvc.perform(
                            put(getControllerURI() + "/" + reservation.getId() + "/lost")
                                    .content(getBodyContent(reservation.getId())))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            ReservationResponse reservationResponse = getResponseObject(mvcResult, ReservationResponse.class);
            assertThat(reservationResponse)
                    .usingRecursiveComparison()
                    .ignoringFields("createdDate", "termDate", "updatedDate")
                    .isEqualTo(givenReservationResponse);
        }
    }
}


