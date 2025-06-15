import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Book} from '../models/book.model';
import {Reservation} from '../models/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + '/books';

  private getAuthHeaders() {
    const token = localStorage.getItem('jwt');
    return token ? {headers: new HttpHeaders({Authorization: `Bearer ${token}`})} : {};
  }

  getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.apiUrl, this.getAuthHeaders());
  }

  getBookById(bookId: number): Observable<Book> {
    return this.http.get<Book>(`${this.apiUrl}/${bookId}`, this.getAuthHeaders());
  }

  createBook(book: Book): Observable<Book> {
    return this.http.post<Book>(this.apiUrl, book, this.getAuthHeaders());
  }

  reserveBook(reservation: { bookId: number, userId: number }): Observable<Reservation> {
    return this.http.post<Reservation>(environment.apiUrl + '/reservations', reservation, this.getAuthHeaders());
  }

  changeBookStatus(bookId: number): Observable<Book> {
    return this.http.put<Book>(`${this.apiUrl}/${bookId}/status`, {}, this.getAuthHeaders());
  }
}
