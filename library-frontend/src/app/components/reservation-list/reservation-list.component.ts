import {Component, OnInit} from '@angular/core';
import {Reservation, ReservationService} from '../../services/reservation.service';
import {ActivatedRoute} from '@angular/router';

function parseJwt(token: string): any {
  try {
    return JSON.parse(atob(token.split('.')[1]));
  } catch {
    return {};
  }
}

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit {
  reservations: Reservation[] = [];
  isUserReservations = false;
  notification: { message: string, color: string } | null = null;

  constructor(
    private reservationService: ReservationService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.isUserReservations = this.route.snapshot.url[0]?.path === 'my-reservations';
    this.loadReservations();
  }

  loadReservations(): void {
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

  get isLibrarian(): boolean {
    const jwt = localStorage.getItem('jwt');
    if (!jwt) return false;
    const user = parseJwt(jwt);
    return user.role === 'ROLE_LIBRARIAN';
  }

  get isUser(): boolean {
    const jwt = localStorage.getItem('jwt');
    if (!jwt) return false;
    const user = parseJwt(jwt);
    return user.role === 'ROLE_USER';
  }

  showNotification(message: string, color: string) {
    this.notification = {message, color};
    setTimeout(() => this.notification = null, 3000);
  }

  canExtend(reservation: Reservation): boolean {
    return this.isUser && (reservation.status === 'NEW' || reservation.status === 'RECEIVED');
  }

  canCancel(reservation: Reservation): boolean {
    return this.isUser && reservation.status === 'NEW';
  }

  canIssue(reservation: Reservation): boolean {
    return this.isLibrarian && reservation.status === 'NEW';
  }

  canComplete(reservation: Reservation): boolean {
    return this.isLibrarian && ['RECEIVED', 'OVERDUE'].includes(reservation.status);
  }

  canMarkAsLost(reservation: Reservation): boolean {
    return this.isLibrarian && reservation.status === 'RECEIVED';
  }

  extendReservation(id: number) {
    this.reservationService.extendBook(id).subscribe({
      next: () => {
        this.showNotification('Reservation extended successfully!', 'green');
        this.loadReservations();
      },
      error: () => this.showNotification('Failed to extend reservation.', 'red')
    });
  }

  cancelReservation(id: number) {
    this.reservationService.cancelReservation(id).subscribe({
      next: () => {
        this.showNotification('Reservation cancelled successfully!', 'green');
        this.loadReservations();
      },
      error: () => this.showNotification('Failed to cancel reservation.', 'red')
    });
  }

  issueBook(id: number) {
    this.reservationService.receiveBook(id).subscribe({
      next: () => {
        this.showNotification('Book issued successfully!', 'green');
        this.loadReservations();
      },
      error: () => this.showNotification('Failed to issue book.', 'red')
    });
  }

  completeReservation(id: number) {
    this.reservationService.completeReservation(id).subscribe({
      next: () => {
        this.showNotification('Reservation completed successfully!', 'green');
        this.loadReservations();
      },
      error: () => this.showNotification('Failed to complete reservation.', 'red')
    });
  }

  markAsLost(id: number) {
    this.reservationService.loseBook(id).subscribe({
      next: () => {
        this.showNotification('Book marked as lost successfully!', 'green');
        this.loadReservations();
      },
      error: () => this.showNotification('Failed to mark book as lost.', 'red')
    });
  }
}
