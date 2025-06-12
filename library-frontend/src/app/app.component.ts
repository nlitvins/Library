import {Component, inject, OnDestroy} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from './services/auth.service';
import {Subscription} from 'rxjs';
import {AuthUtilsService, JwtPayload} from './services/auth-utils.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnDestroy {
  private router = inject(Router);
  private authService = inject(AuthService);
  private authUtils = inject(AuthUtilsService);

  title = 'library-frontend';
  showLogin = false;
  showRegister = false;
  jwt: string | null = this.authService.getToken();
  user: JwtPayload | null = this.jwt ? this.authUtils.parseJwt(this.jwt) : null;
  private authSubscription: Subscription;

  constructor() {
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
    return this.authUtils.getDisplayName();
  }

  get isUser(): boolean {
    return this.authUtils.isUser();
  }

  get isLibrarian(): boolean {
    return this.authUtils.isLibrarian();
  }

  get isAdmin(): boolean {
    return this.authUtils.isAdmin();
  }

  onLogin({token}: { token: string }) {
    this.authService.setToken(token);
    this.jwt = token;
    this.user = this.authUtils.parseJwt(token);
    this.showLogin = false;
  }

  onRegister({token}: { token: string }) {
    this.authService.setToken(token);
    this.jwt = token;
    this.user = this.authUtils.parseJwt(token);
    this.showRegister = false;
  }

  logout() {
    this.authService.logout();
  }
}
