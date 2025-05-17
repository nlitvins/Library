import {Component, OnInit} from '@angular/core';
import {ReservationService, Reservation} from '../../services/reservation.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-reservation-form',
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.scss']
})
export class ReservationFormComponent implements OnInit {
  reservation: any = {bookId: ''};
  loading = false;
  error: string | null = null;

  constructor(private reservationService: ReservationService, private router: Router) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.loading = true;
    this.error = null;
    this.reservationService.reserveBook(this.reservation).subscribe({
      next: () => this.router.navigate(['/reservations']),
      error: err => {
        this.error = 'Failed to create reservation.';
        this.loading = false;
      }
    });
  }
}
