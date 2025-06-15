import {Component, inject, OnInit} from '@angular/core';
import {ReservationService} from '../../services/reservation.service';
import {ReservationDetailed} from '../../models/reservation.model';
import {ActivatedRoute} from '@angular/router';
import {AuthUtilsService} from '../../services/auth-utils.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit {
  private reservationService = inject(ReservationService);
  private route = inject(ActivatedRoute);
  private authUtils = inject(AuthUtilsService);
  private translate = inject(TranslateService);

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
    const scrollPosition = window.scrollY;
    if (this.isUserReservations) {
      this.reservationService.getReservationsDetailedByCurrentUser()
        .subscribe(data => {
          this.reservations = data;
          this.applyFilters();
          window.scrollTo(0, scrollPosition);
        });
    } else {
      this.reservationService.getReservations()
        .subscribe(data => {
          this.reservations = data;
          this.applyFilters();
          window.scrollTo(0, scrollPosition);
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
        this.showNotification(this.translate.instant('reservations.notifications.extendSuccess'), 'green');
        this.loadReservations();
      },
      error: () => this.showNotification(this.translate.instant('reservations.notifications.extendError'), 'red')
    });
  }

  cancelReservation(id: number) {
    this.reservationService.cancelReservation(id).subscribe({
      next: () => {
        this.showNotification(this.translate.instant('reservations.notifications.cancelSuccess'), 'green');
        this.loadReservations();
      },
      error: () => this.showNotification(this.translate.instant('reservations.notifications.cancelError'), 'red')
    });
  }

  issueBook(id: number) {
    this.reservationService.receiveBook(id).subscribe({
      next: () => {
        this.showNotification(this.translate.instant('reservations.notifications.issueSuccess'), 'green');
        this.loadReservations();
      },
      error: () => this.showNotification(this.translate.instant('reservations.notifications.issueError'), 'red')
    });
  }

  completeReservation(id: number) {
    this.reservationService.completeReservation(id).subscribe({
      next: () => {
        this.showNotification(this.translate.instant('reservations.notifications.completeSuccess'), 'green');
        this.loadReservations();
      },
      error: () => this.showNotification(this.translate.instant('reservations.notifications.completeError'), 'red')
    });
  }

  markAsLost(id: number) {
    this.reservationService.loseBook(id).subscribe({
      next: () => {
        this.showNotification(this.translate.instant('reservations.notifications.lostSuccess'), 'green');
        this.loadReservations();
      },
      error: () => this.showNotification(this.translate.instant('reservations.notifications.lostError'), 'red')
    });
  }
}
