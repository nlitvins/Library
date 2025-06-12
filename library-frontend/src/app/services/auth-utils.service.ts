import {Injectable} from '@angular/core';

export interface JwtPayload {
  role?: string;
  userId?: number;
  name?: string;
  secondName?: string;
  email?: string;
  exp?: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthUtilsService {
  private readonly ROLE_USER = 'ROLE_USER';
  private readonly ROLE_LIBRARIAN = 'ROLE_LIBRARIAN';
  private readonly ROLE_ADMIN = 'ROLE_ADMIN';

  parseJwt(token: string): JwtPayload {
    try {
      return JSON.parse(atob(token.split('.')[1]));
    } catch {
      return {};
    }
  }

  getCurrentUser(): JwtPayload | null {
    const jwt = localStorage.getItem('jwt');
    if (!jwt) return null;
    return this.parseJwt(jwt);
  }

  isUser(): boolean {
    const user = this.getCurrentUser();
    return user?.role === this.ROLE_USER;
  }

  isLibrarian(): boolean {
    const user = this.getCurrentUser();
    return user?.role === this.ROLE_LIBRARIAN;
  }

  isAdmin(): boolean {
    const user = this.getCurrentUser();
    return user?.role === this.ROLE_ADMIN;
  }

  getDisplayName(): string {
    const user = this.getCurrentUser();
    return user ? `${user.name || ''} ${user.secondName || ''}`.trim() : '';
  }
}
