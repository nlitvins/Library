import {Component, OnInit} from '@angular/core';
import {Book, BookGenre, BookService, BookStatus, BookType} from '../../services/book.service';

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
  filteredBooks: Book[] = [];
  filters = {
    title: '',
    author: '',
    quantity: '',
    genre: '',
    type: '',
    status: '',
    pages: '',
    edition: ''
  };
  bookStatuses = Object.values(BookStatus);
  bookGenres = Object.values(BookGenre);
  bookTypes = Object.values(BookType);
  notification: { message: string, color: string } | null = null;

  constructor(private bookService: BookService) {
  }

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(): void {
    this.bookService.getBooks().subscribe({
      next: (books) => {
        this.books = books;
        this.filteredBooks = books;
      },
      error: (error) => {
        this.showNotification('Error loading books: ' + error.message, 'red');
      }
    });
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

  showNotification(message: string, color: string): void {
    this.notification = {message, color};
    setTimeout(() => {
      this.notification = null;
    }, 3000);
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

  applyFilter(): void {
    this.filteredBooks = this.books.filter(book => {
      const titleMatch = !this.filters.title ||
        book.title.toLowerCase().includes(this.filters.title.toLowerCase().trim());
      const authorMatch = !this.filters.author ||
        book.author.toLowerCase().includes(this.filters.author.toLowerCase().trim());
      const quantityMatch = !this.filters.quantity ||
        book.quantity >= parseInt(this.filters.quantity);
      const genreMatch = !this.filters.genre ||
        book.genre === this.filters.genre;
      const typeMatch = !this.filters.type ||
        book.type === this.filters.type;
      const statusMatch = !this.filters.status ||
        book.status === this.filters.status;
      const pagesMatch = !this.filters.pages ||
        book.pages >= parseInt(this.filters.pages);
      const editionMatch = !this.filters.edition ||
        book.edition.toLowerCase().includes(this.filters.edition.toLowerCase().trim());

      return titleMatch && authorMatch && quantityMatch && genreMatch &&
        typeMatch && statusMatch && pagesMatch && editionMatch;
    });
  }
}
