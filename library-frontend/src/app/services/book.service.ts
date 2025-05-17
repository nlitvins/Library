import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

export interface Book {
  id?: number;
  title: string;
  author: string;
  quantity: number;
  // Add other fields as needed
}

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiUrl = environment.apiUrl + '/books';

  constructor(private http: HttpClient) {
  }

  getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.apiUrl);
  }

  getBookById(bookId: number): Observable<Book> {
    return this.http.get<Book>(`${this.apiUrl}/${bookId}`);
  }

  addBook(book: Book): Observable<Book> {
    return this.http.post<Book>(this.apiUrl, book);
  }
}
