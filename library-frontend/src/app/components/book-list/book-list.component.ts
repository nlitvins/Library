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
  notification: { message: string, color: string } | null = null;

    // enum values
    bookStatuses = Object.values(BookStatus);
    bookGenres = Object.values(BookGenre);
    bookTypes = Object.values(BookType);

    // Filter properties
    titleFilter: string = '';
    authorFilter: string = '';
    genreFilter: string = '';
    typeFilter: string = '';
    minPagesFilter: number | null = null;
    minQuantityFilter: number | null = null;
    statusFilter: string = '';

  constructor(private bookService: BookService) {
  }

  ngOnInit(): void {
      this.bookService.getBooks().subscribe(data => {
          this.books = data;
          this.applyFilters();
      });
  }

    applyFilters() {
        this.filteredBooks = this.books.filter(book => {
            const matchesTitle = !this.titleFilter ||
                book.title.toLowerCase().includes(this.titleFilter.toLowerCase());

            const matchesAuthor = !this.authorFilter ||
                book.author.toLowerCase().includes(this.authorFilter.toLowerCase());

            const matchesGenre = !this.genreFilter ||
                book.genre === this.genreFilter;

            const matchesType = !this.typeFilter ||
                book.type === this.typeFilter;

            const matchesPages = !this.minPagesFilter ||
                book.pages >= this.minPagesFilter;

            const matchesQuantity = !this.minQuantityFilter ||
                book.quantity >= this.minQuantityFilter;

            const matchesStatus = !this.statusFilter ||
                book.status === this.statusFilter;

            return matchesTitle && matchesAuthor && matchesGenre &&
                matchesType && matchesPages && matchesQuantity &&
                matchesStatus;
        });
    }

    clearFilters() {
        this.titleFilter = '';
        this.authorFilter = '';
        this.genreFilter = '';
        this.typeFilter = '';
        this.minPagesFilter = null;
        this.minQuantityFilter = null;
        this.statusFilter = '';
        this.applyFilters();
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
