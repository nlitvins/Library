import {Component, OnInit} from '@angular/core';
import {Reservation, ReservationService} from '../../services/reservation.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit {
  reservations: Reservation[] = [];
  isUserReservations = false;

  constructor(
    private reservationService: ReservationService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.isUserReservations = this.route.snapshot.url[0]?.path === 'my-reservations';

    if (this.isUserReservations) {
      const jwt = localStorage.getItem('jwt');
      if (!jwt) return;
      this.reservationService.getReservationsByUserId()
        .subscribe(data => this.reservations = data);
    } else {
      this.reservationService.getReservations()
        .subscribe(data => this.reservations = data);
    }
  }
}
