import {Component, inject, OnInit} from '@angular/core';
import {User, UserService} from '../../services/user.service';
import {ReservationDetailed, ReservationService} from '../../services/reservation.service';
import {AuthUtilsService} from '../../services/auth-utils.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  private userService = inject(UserService);
  private reservationService = inject(ReservationService);
  private authUtils = inject(AuthUtilsService);

  users: User[] = [];
  filteredUsers: User[] = [];
  expandedUsers = new Set<number>();
  userReservations = new Map<number, ReservationDetailed[]>();
  loadingReservations = new Set<number>();
  notification: { message: string, color: string } | null = null;

  // Filter properties
  nameFilter = '';
  emailFilter = '';
  roleFilter = '';
  personCodeFilter = '';

  ngOnInit(): void {
    this.userService.getUsers().subscribe(data => {
      this.users = data;
      this.applyFilters();
    });
  }

  applyFilters() {
    this.filteredUsers = this.users.filter(user => {
      const fullName = `${user.name} ${user.secondName}`.toLowerCase();
      const matchesName = !this.nameFilter ||
        fullName.includes(this.nameFilter.toLowerCase()) ||
        user.userName.toLowerCase().includes(this.nameFilter.toLowerCase());

      const matchesEmail = !this.emailFilter ||
        user.email.toLowerCase().includes(this.emailFilter.toLowerCase());

      const matchesRole = !this.roleFilter ||
        user.role === this.roleFilter;

      const matchesPersonCode = !this.personCodeFilter ||
        user.personCode.toLowerCase().includes(this.personCodeFilter.toLowerCase());

      return matchesName && matchesEmail && matchesRole && matchesPersonCode;
    });
  }

  clearFilters() {
    this.nameFilter = '';
    this.emailFilter = '';
    this.roleFilter = '';
    this.personCodeFilter = '';
    this.applyFilters();
  }

  get isAdmin(): boolean {
    return this.authUtils.isAdmin();
  }

  get isLibrarian(): boolean {
    return this.authUtils.isLibrarian();
  }

  toggleUserExpansion(userId: number): void {
    if (this.expandedUsers.has(userId)) {
      this.expandedUsers.delete(userId);
    } else {
      this.expandedUsers.add(userId);
      this.loadUserReservations(userId);
    }
  }

  loadUserReservations(userId: number): void {
    if (this.loadingReservations.has(userId)) return;

    this.loadingReservations.add(userId);
    this.reservationService.getReservationsDetailedByUserId(userId).subscribe({
      next: (reservations) => {
        this.userReservations.set(userId, reservations);
        this.loadingReservations.delete(userId);
      },
      error: () => {
        this.loadingReservations.delete(userId);
      }
    });
  }

  isUserExpanded(userId: number): boolean {
    return this.expandedUsers.has(userId);
  }

  getUserReservations(userId: number): ReservationDetailed[] {
    return this.userReservations.get(userId) || [];
  }

  isLoadingReservations(userId: number): boolean {
    return this.loadingReservations.has(userId);
  }

  onLibrarianCreated() {
    this.userService.getUsers().subscribe(data => this.users = data);
  }

  canActivateUser(user: User): boolean {
    return this.isLibrarian && user.role === 'STARTER';
  }

  canActivateLibrarian(user: User): boolean {
    return this.isAdmin && user.role === 'STARTER';
  }

  activateUser(userId: number): void {
    this.userService.activateUser(userId).subscribe(() => {
      this.userService.getUsers().subscribe(data => this.users = data);
    });
  }

  activateLibrarian(userId: number): void {
    this.userService.activateLibrarian(userId).subscribe(() => {
      this.userService.getUsers().subscribe(data => this.users = data);
    });
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

  issueBook(id: number) {
    this.reservationService.receiveBook(id).subscribe({
      next: () => {
        this.showNotification('Book issued successfully!', 'green');
        this.loadUserReservations(id);
      },
      error: () => this.showNotification('Failed to issue book.', 'red')
    });
  }

  completeReservation(id: number) {
    this.reservationService.completeReservation(id).subscribe({
      next: () => {
        this.showNotification('Reservation completed successfully!', 'green');
        this.loadUserReservations(id);
      },
      error: () => this.showNotification('Failed to complete reservation.', 'red')
    });
  }

  markAsLost(id: number) {
    this.reservationService.loseBook(id).subscribe({
      next: () => {
        this.showNotification('Book marked as lost successfully!', 'green');
        this.loadUserReservations(id);
      },
      error: () => this.showNotification('Failed to mark book as lost.', 'red')
    });
  }

  showNotification(message: string, color: string) {
    this.notification = {message, color};
    setTimeout(() => this.notification = null, 3000);
  }
}
