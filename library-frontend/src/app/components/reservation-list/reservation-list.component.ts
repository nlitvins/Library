import {Component, OnInit} from '@angular/core';
import {ReservationService, Reservation} from '../../services/reservation.service';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit {
  reservations: Reservation[] = [];

  constructor(private reservationService: ReservationService) {
  }

  ngOnInit(): void {
    this.reservationService.getReservations().subscribe(data => this.reservations = data);
  }
}
