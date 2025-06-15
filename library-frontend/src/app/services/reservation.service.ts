import {HttpClient, HttpHeaders} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Reservation, ReservationDetailed} from '../models/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private apiUrl = environment.apiUrl + '/reservations';

  private http = inject(HttpClient);

  private getAuthHeaders() {
    const token = localStorage.getItem('jwt');
    return token ? {headers: new HttpHeaders({Authorization: `Bearer ${token}`})} : {};
  }

  getReservations(): Observable<ReservationDetailed[]> {
    return this.http.get<ReservationDetailed[]>(`${this.apiUrl}/detailed`, this.getAuthHeaders());
  }

  getReservationsDetailedByUserId(userId: number): Observable<ReservationDetailed[]> {
    return this.http.get<ReservationDetailed[]>(`${this.apiUrl}/detailed/user/${userId}`, this.getAuthHeaders());
  }

  getReservationsDetailedByCurrentUser(): Observable<ReservationDetailed[]> {
    return this.http.get<ReservationDetailed[]>(`${this.apiUrl}/detailed/user`, this.getAuthHeaders());
  }

  receiveBook(id: number): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}/receiving`, {}, this.getAuthHeaders());
  }

  extendBook(id: number): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}/extension`, {}, this.getAuthHeaders());
  }

  completeReservation(id: number): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}/completed`, {}, this.getAuthHeaders());
  }

  cancelReservation(id: number): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}/cancel`, {}, this.getAuthHeaders());
  }

  loseBook(id: number): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}/lost`, {}, this.getAuthHeaders());
  }
}
