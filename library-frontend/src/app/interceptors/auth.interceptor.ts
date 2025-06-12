import {inject, Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Router} from '@angular/router';
import {AuthService} from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private router = inject(Router);
  private authService = inject(AuthService);

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        // Handle both CORS errors (status 0) and 401 responses
        if (error.status === 0 || error.status === 401) {
          // Check if the error is from our API
          if (error.url?.includes('localhost:8080')) {
            this.authService.logout();
          }
        }
        return throwError(() => error);
      })
    );
  }
}
