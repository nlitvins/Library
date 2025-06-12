import {inject, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs';

interface JwtPayload {
  exp?: number;
}

function parseJwt(token: string): JwtPayload {
  try {
    return JSON.parse(atob(token.split('.')[1]));
  } catch {
    return {};
  }
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private router = inject(Router);
  private tokenExpirationCheckInterval: number | undefined;
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor() {
    this.checkToken();
  }

  private checkToken() {
    const token = localStorage.getItem('jwt');
    if (token) {
      const decodedToken = parseJwt(token);
      const expirationTime = decodedToken.exp ? decodedToken.exp * 1000 : 0; // Convert to milliseconds
      const currentTime = Date.now();

      if (currentTime >= expirationTime) {
        this.logout();
      } else {
        this.isAuthenticatedSubject.next(true);
        // Set up periodic token check
        this.startTokenExpirationCheck();
      }
    } else {
      this.isAuthenticatedSubject.next(false);
    }
  }

  private startTokenExpirationCheck() {
    // Clear any existing interval
    if (this.tokenExpirationCheckInterval) {
      clearInterval(this.tokenExpirationCheckInterval);
    }

    // Check token every minute
    this.tokenExpirationCheckInterval = window.setInterval(() => {
      const token = localStorage.getItem('jwt');
      if (token) {
        const decodedToken = parseJwt(token);
        const expirationTime = decodedToken.exp ? decodedToken.exp * 1000 : 0;
        const currentTime = Date.now();

        if (currentTime >= expirationTime) {
          this.logout();
        }
      }
    }, 60000); // Check every minute
  }

  setToken(token: string) {
    localStorage.setItem('jwt', token);
    this.checkToken();
  }

  logout() {
    localStorage.removeItem('jwt');
    this.isAuthenticatedSubject.next(false);
    if (this.tokenExpirationCheckInterval) {
      clearInterval(this.tokenExpirationCheckInterval);
    }
    this.router.navigate(['/books']);
  }

  getToken(): string | null {
    return localStorage.getItem('jwt');
  }

  isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) return true;

    const decodedToken = parseJwt(token);
    const expirationTime = decodedToken.exp ? decodedToken.exp * 1000 : 0;
    return Date.now() >= expirationTime;
  }
}
