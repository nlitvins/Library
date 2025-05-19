import {Component, OnInit} from '@angular/core';
import {Book, BookService} from '../../services/book.service';

function parseJwt(token: string): any {
  try {
    return JSON.parse(atob(token.split('.')[1]));
  } catch {
    return {};
  }
}

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {
  books: Book[] = [];
  notification: { message: string, color: string } | null = null;

  constructor(private bookService: BookService) {
  }

  ngOnInit(): void {
    this.bookService.getBooks().subscribe(data => this.books = data);
  }

  get isLibrarian(): boolean {
    const jwt = localStorage.getItem('jwt');
    if (!jwt) return false;
    const user = parseJwt(jwt);
    return user.role === 'ROLE_LIBRARIAN';
  }

  get isUser(): boolean {
    const jwt = localStorage.getItem('jwt');
    if (!jwt) return false;
    const user = parseJwt(jwt);
    return user.role === 'ROLE_USER';
  }

  showNotification(message: string, color: string) {
    this.notification = {message, color};
    setTimeout(() => this.notification = null, 3000);
  }

  reserveBook(bookId?: number) {
    if (bookId === undefined) return;
    const jwt = localStorage.getItem('jwt');
    if (!jwt) return;
    const user = parseJwt(jwt);
    if (!user.userId) return;
    this.bookService.reserveBook({bookId, userId: user.userId}).subscribe({
      next: () => this.showNotification('Reservation successful!', 'green'),
      error: () => this.showNotification('Failed to reserve book.', 'red')
    });
  }
}
