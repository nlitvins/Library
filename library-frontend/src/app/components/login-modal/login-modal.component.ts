import {Component, EventEmitter, inject, Output} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-login-modal',
  templateUrl: './login-modal.component.html',
  styleUrls: ['./login-modal.component.scss']
})
export class LoginModalComponent {
  @Output() modalClose = new EventEmitter<void>();
  @Output() loginSuccess = new EventEmitter<{ token: string, username: string }>();

  private http = inject(HttpClient);

  username = '';
  password = '';
  loading = false;
  error: string | null = null;

  $emitClose() {
    this.modalClose.emit();
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
        this.modalClose.emit();
      },
      error: () => {
        this.error = 'Login failed. Please check your credentials.';
        this.loading = false;
      }
    });
  }
}
