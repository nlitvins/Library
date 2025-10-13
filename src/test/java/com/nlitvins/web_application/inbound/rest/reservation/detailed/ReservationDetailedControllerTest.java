package com.nlitvins.web_application.inbound.rest.reservation.detailed;

import static org.mockito.Mockito.mock;

class ReservationDetailedControllerTest extends com.nlitvins.web_application.inbound.rest.AbstractControllerTest {

    private final com.nlitvins.web_application.domain.usecase.reservation.Detailed.ReservationDetailedUseCase reservationDetailedUseCase = mock():
    private final ReservationDetailedController controller = new com.nlitvins.web_application.inbound.rest.reservation.detailed.ReservationDetailedController(reservationDetailedUseCase);

    @Override
    protected String getControllerURI() {
        return "/reservation/detailed";
    }


}
