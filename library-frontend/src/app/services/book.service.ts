import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

export enum BookStatus {
  AVAILABLE = 'AVAILABLE',
  NOT_AVAILABLE = 'NOT_AVAILABLE'
}

export enum BookGenre {
  ROMANCE = 'ROMANCE',
  MODERNIST = 'MODERNIST',
  CLASSIC = 'CLASSIC',
  MAGICAL_REALISM = 'MAGICAL_REALISM',
  DYSTOPIAN = 'DYSTOPIAN',
  FANTASY = 'FANTASY',
  ADVENTURE = 'ADVENTURE',
  PHILOSOPHICAL = 'PHILOSOPHICAL',
  ANCIENT = 'ANCIENT',
  RELIGIOUS = 'RELIGIOUS'
}

export enum BookType {
  BOOK = 'BOOK',
  MAGAZINE = 'MAGAZINE',
  NEWSPAPER = 'NEWSPAPER'
}

export interface Book {
    id: number;
  title: string;
  author: string;
  quantity: number;
  creationYear: string;
  status: BookStatus;
  genre: BookGenre;
  pages: number;
  edition: string;
  releaseDate: string;
  type: BookType;
}

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiUrl = environment.apiUrl + '/books';

  constructor(private http: HttpClient) {
  }

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

  reserveBook(reservation: { bookId: number, userId: number }): Observable<any> {
    return this.http.post(environment.apiUrl + '/reservations', reservation, this.getAuthHeaders());
  }
}
