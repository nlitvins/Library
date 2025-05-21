import {Component} from '@angular/core';
import {Router} from '@angular/router';

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
export class AppComponent {
  title = 'library-frontend';
  showLogin = false;
  jwt: string | null = localStorage.getItem('jwt');
  user: any = this.jwt ? parseJwt(this.jwt) : null;

  constructor(private router: Router) {
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
    this.jwt = token;
    localStorage.setItem('jwt', token);
    this.user = parseJwt(token);
  }

  logout() {
    this.jwt = null;
    this.user = null;
    localStorage.removeItem('jwt');
    this.router.navigate(['/books']);
  }
}
