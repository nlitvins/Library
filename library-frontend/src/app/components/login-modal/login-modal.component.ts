import {Component, EventEmitter, Output} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-login-modal',
  templateUrl: './login-modal.component.html',
  styleUrls: ['./login-modal.component.scss']
})
export class LoginModalComponent {
  @Output() close = new EventEmitter<void>();
  @Output() loginSuccess = new EventEmitter<{ token: string, username: string }>();

  username = '';
  password = '';
  loading = false;
  error: string | null = null;

  constructor(private http: HttpClient) {
  }

  $emitClose() {
    this.close.emit();
  }

  onSubmit() {
    this.loading = true;
    this.error = null;
    this.http.post<{ token: string }>(`${environment.apiUrl}/login`, {
      username: this.username,
      password: this.password
    }).subscribe({
      next: (res) => {
        localStorage.setItem('jwt', res.token);
        this.loginSuccess.emit({token: res.token, username: this.username});
        this.close.emit();
      },
      error: (err) => {
        this.error = 'Login failed. Please check your credentials.';
        this.loading = false;
      }
    });
  }
}
