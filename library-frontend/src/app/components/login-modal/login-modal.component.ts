import {Component, EventEmitter, inject, Output} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-login-modal',
  templateUrl: './login-modal.component.html',
  styleUrls: ['./login-modal.component.scss']
})
export class LoginModalComponent {
  @Output() modalClose = new EventEmitter<void>();
  @Output() loginSuccess = new EventEmitter<{ token: string, username: string }>();

  private http = inject(HttpClient);
  private translate = inject(TranslateService);

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
        this.error = this.translate.instant('auth.loginFailed');
        this.loading = false;
      }
    });
  }
}
