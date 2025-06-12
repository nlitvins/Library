import {Component, inject, OnInit} from '@angular/core';
import {User, UserService} from '../../services/user.service';
import {Reservation, ReservationService} from '../../services/reservation.service';
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
  expandedUsers = new Set<number>();
  userReservations = new Map<number, Reservation[]>();
  loadingReservations = new Set<number>();

  ngOnInit(): void {
    this.userService.getUsers().subscribe(data => this.users = data);
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
    this.reservationService.getReservationsByUserId(userId).subscribe({
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

  getUserReservations(userId: number): Reservation[] {
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
}
