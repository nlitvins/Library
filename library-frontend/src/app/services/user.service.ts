import {HttpClient, HttpHeaders} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

export interface User {
  id: number;
  name: string;
  secondName: string;
  userName: string;
  email: string;
  mobileNumber: string;
  personCode: string;
  role: string;
}

export interface CreateUserRequest {
  name: string;
  secondName: string;
  userName: string;
  email: string;
  mobileNumber: string;
  personCode: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = environment.apiUrl + '/users';

  private http = inject(HttpClient);

  private getAuthHeaders() {
    const token = localStorage.getItem('jwt');
    return token ? {headers: new HttpHeaders({Authorization: `Bearer ${token}`})} : {};
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl, this.getAuthHeaders());
  }

  registerUser(user: CreateUserRequest): Observable<User> {
    return this.http.post<User>(this.apiUrl, user, this.getAuthHeaders());
  }

  activateUser(userId: number): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${userId}`, this.getAuthHeaders());
  }

  activateLibrarian(userId: number): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${userId}/librarian`, this.getAuthHeaders());
  }

}
