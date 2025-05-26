import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

export interface User {
  id?: number;
  name: string;
  secondName: string;
  email: string;
  phone: string;
  role: string;
  createdAt?: string;
  updatedAt?: string;
  // Add other fields as needed
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = environment.apiUrl + '/users';

  constructor(private http: HttpClient) {
  }

  private getAuthHeaders() {
    const token = localStorage.getItem('jwt');
    return token ? {headers: new HttpHeaders({Authorization: `Bearer ${token}`})} : {};
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl, this.getAuthHeaders());
  }

  registerUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user, this.getAuthHeaders());
  }
}
