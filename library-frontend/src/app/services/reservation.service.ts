import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

export interface Reservation {
  id?: number;
  // Add other fields as needed
}

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private apiUrl = environment.apiUrl + '/reservations';

  constructor(private http: HttpClient) {
  }

  getReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.apiUrl);
  }

  reserveBook(reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(this.apiUrl, reservation);
  }

  receiveBook(id: number): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}/receiving`, {});
  }

  extendBook(id: number): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}/extension`, {});
  }

  completeReservation(id: number): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}/completed`, {});
  }

  cancelReservation(id: number): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}/cancel`, {});
  }

  loseBook(id: number): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}/lost`, {});
  }
}
