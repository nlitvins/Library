import {Component, OnInit} from '@angular/core';
import {ReservationDetailed, ReservationService} from '../../services/reservation.service';
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
  reservations: ReservationDetailed[] = [];
  filteredReservations: ReservationDetailed[] = [];
  filters = {
    bookTitle: '',
    bookAuthor: '',
    userName: '',
    status: '',
    id: ''
  };
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
      const user = parseJwt(jwt);
      this.reservationService.getReservationsByCurrentUser()
        .subscribe(data => {
          // For user reservations, we need to create a detailed view with the same book info
          this.reservations = data.map(r => ({
            reservation: r,
            book: {
              id: r.bookId || 0,
              title: '',  // Not available in basic reservation
              author: '',  // Not available in basic reservation
              quantity: 0  // Not available in basic reservation
            },
            user: {
              id: user.id,
              name: user.name,
              secondName: user.secondName,
              email: user.email
            }
          }));
          this.filteredReservations = this.reservations;
        });
    } else {
      this.reservationService.getReservations()
        .subscribe(data => {
          this.reservations = data;
          this.filteredReservations = data;
        });
    }
  }

  applyFilter(): void {
    this.filteredReservations = this.reservations.filter(reservation => {
      const bookTitleMatch = !this.filters.bookTitle ||
        reservation.book.title.toLowerCase().includes(this.filters.bookTitle.toLowerCase().trim());
      const bookAuthorMatch = !this.filters.bookAuthor ||
        reservation.book.author.toLowerCase().includes(this.filters.bookAuthor.toLowerCase().trim());
      const userNameMatch = !this.filters.userName ||
        (reservation.user.name + ' ' + reservation.user.secondName).toLowerCase().includes(this.filters.userName.toLowerCase().trim());
      const statusMatch = !this.filters.status ||
        reservation.reservation.status.toLowerCase().includes(this.filters.status.toLowerCase().trim());
      const idMatch = !this.filters.id ||
        reservation.reservation.id.toString().includes(this.filters.id.trim());

      return bookTitleMatch && bookAuthorMatch && userNameMatch && statusMatch && idMatch;
    });
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

  canExtend(reservation: ReservationDetailed): boolean {
    return this.isUser && (reservation.reservation.status === 'NEW' || reservation.reservation.status === 'RECEIVED');
  }

  canCancel(reservation: ReservationDetailed): boolean {
    return this.isUser && reservation.reservation.status === 'NEW';
  }

  canIssue(reservation: ReservationDetailed): boolean {
    return this.isLibrarian && reservation.reservation.status === 'NEW';
  }

  canComplete(reservation: ReservationDetailed): boolean {
    return this.isLibrarian && ['RECEIVED', 'OVERDUE'].includes(reservation.reservation.status);
  }

  canMarkAsLost(reservation: ReservationDetailed): boolean {
    return this.isLibrarian && reservation.reservation.status === 'RECEIVED';
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
