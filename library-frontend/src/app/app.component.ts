import {Component, OnDestroy} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from './services/auth.service';
import {Subscription} from 'rxjs';

function parseJwt(token: string): any {
  try {
    return JSON.parse(atob(token.split('.')[1]));
  } catch {
    return {};
  }
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnDestroy {
  title = 'library-frontend';
  showLogin = false;
  jwt: string | null = this.authService.getToken();
  user: any = this.jwt ? parseJwt(this.jwt) : null;
  private authSubscription: Subscription;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {
    this.authSubscription = this.authService.isAuthenticated$.subscribe(isAuthenticated => {
      if (!isAuthenticated) {
        this.user = null;
        this.jwt = null;
        this.router.navigate(['/books']);
      }
    });
  }

  ngOnDestroy() {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  get displayName(): string {
    return this.user ? `${this.user.name || ''} ${this.user.secondName || ''}`.trim() : '';
  }

  get isUser(): boolean {
    return this.user?.role === 'ROLE_USER';
  }

  get isLibrarian(): boolean {
    return this.user?.role === 'ROLE_LIBRARIAN';
  }

  get isAdmin(): boolean {
    return this.user?.role === 'ROLE_ADMIN';
  }

  onLogin({token}: { token: string }) {
    this.authService.setToken(token);
    this.jwt = token;
    this.user = parseJwt(token);
  }

  logout() {
    this.authService.logout();
  }
}
