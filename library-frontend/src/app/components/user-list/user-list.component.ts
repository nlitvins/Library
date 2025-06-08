import {Component, OnInit} from '@angular/core';
import {User, UserService} from '../../services/user.service';
import {Reservation, ReservationService} from '../../services/reservation.service';

function parseJwt(token: string): any {
  try {
    return JSON.parse(atob(token.split('.')[1]));
  } catch {
    return {};
  }
}

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  filteredUsers: User[] = [];
  filters = {
    name: '',
    email: '',
    phone: '',
    role: ''
  };
  expandedUsers: Set<number> = new Set();
  userReservations: Map<number, Reservation[]> = new Map();
  loadingReservations: Set<number> = new Set();

  constructor(
    private userService: UserService,
    private reservationService: ReservationService
  ) {
  }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(data => {
      this.users = data;
      this.filteredUsers = data;
    });
  }

  get isAdmin(): boolean {
    const jwt = localStorage.getItem('jwt');
    if (!jwt) return false;
    const user = parseJwt(jwt);
    return user.role === 'ROLE_ADMIN';
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

  applyFilter(): void {
    this.filteredUsers = this.users.filter(user => {
      const nameMatch = !this.filters.name ||
        (user.name + ' ' + user.secondName).toLowerCase().includes(this.filters.name.toLowerCase().trim());
      const emailMatch = !this.filters.email ||
        user.email.toLowerCase().includes(this.filters.email.toLowerCase().trim());
      const phoneMatch = !this.filters.phone ||
        user.phone.toLowerCase().includes(this.filters.phone.toLowerCase().trim());
      const roleMatch = !this.filters.role ||
        user.role.toLowerCase().includes(this.filters.role.toLowerCase().trim());

      return nameMatch && emailMatch && phoneMatch && roleMatch;
    });
  }
}
