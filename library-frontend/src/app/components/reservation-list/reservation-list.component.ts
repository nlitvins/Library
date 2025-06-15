import {Component, inject, OnInit} from '@angular/core';
import {ReservationDetailed, ReservationService} from '../../services/reservation.service';
import {ActivatedRoute} from '@angular/router';
import {AuthUtilsService} from '../../services/auth-utils.service';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit {
  private reservationService = inject(ReservationService);
  private route = inject(ActivatedRoute);
  private authUtils = inject(AuthUtilsService);

  reservations: ReservationDetailed[] = [];
  filteredReservations: ReservationDetailed[] = [];
  isUserReservations = false;
  notification: { message: string, color: string } | null = null;

  // Filter properties
  bookTitleFilter = '';
  userNameFilter = '';
  statusFilter = '';
  dateFromFilter = '';
  dateToFilter = '';

  ngOnInit(): void {
    this.isUserReservations = this.route.snapshot.url[0]?.path === 'my-reservations';
    this.loadReservations();
  }

  loadReservations(): void {
    if (this.isUserReservations) {
      this.reservationService.getReservationsDetailedByCurrentUser()
        .subscribe(data => {
          this.reservations = data;
          this.applyFilters();
        });
    } else {
      this.reservationService.getReservations()
        .subscribe(data => {
          this.reservations = data;
          this.applyFilters();
        });
    }
  }

  applyFilters() {
    this.filteredReservations = this.reservations.filter(reservation => {
      const matchesBookTitle = !this.bookTitleFilter ||
        reservation.book.title.toLowerCase().includes(this.bookTitleFilter.toLowerCase());

      let matchesUserName = false;
      if (!this.isUserReservations) {
        const fullName = `${reservation.user.name} ${reservation.user.secondName}`.toLowerCase();
        matchesUserName = !this.userNameFilter ||
          fullName.includes(this.userNameFilter.toLowerCase());
      } else {
        matchesUserName = true;
      }

      const matchesStatus = !this.statusFilter ||
        reservation.reservation.status === this.statusFilter;

      const matchesDateFrom = !this.dateFromFilter ||
        new Date(reservation.reservation.createdDate) >= new Date(this.dateFromFilter);

      const matchesDateTo = !this.dateToFilter ||
        new Date(reservation.reservation.createdDate) <= new Date(this.dateToFilter);

      return matchesBookTitle && matchesUserName && matchesStatus &&
        matchesDateFrom && matchesDateTo;
    });
  }

  clearFilters() {
    this.bookTitleFilter = '';
    this.userNameFilter = '';
    this.statusFilter = '';
    this.dateFromFilter = '';
    this.dateToFilter = '';
    this.applyFilters();
  }

  get isLibrarian(): boolean {
    return this.authUtils.isLibrarian();
  }

  get isUser(): boolean {
    return this.authUtils.isUser();
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
