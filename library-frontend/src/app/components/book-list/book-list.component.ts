import {Component, inject, OnInit} from '@angular/core';
import {Book, BookGenre, BookService, BookStatus, BookType} from '../../services/book.service';
import {AuthUtilsService} from '../../services/auth-utils.service';

interface JwtPayload {
  role?: string;
  userId?: number;
}

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {
  private bookService = inject(BookService);
  private authUtils = inject(AuthUtilsService);

  books: Book[] = [];
  filteredBooks: Book[] = [];
  notification: { message: string, color: string } | null = null;

  // enum values
  bookStatuses = Object.values(BookStatus);
  bookGenres = Object.values(BookGenre);
  bookTypes = Object.values(BookType);

  // Filter properties
  titleFilter = '';
  authorFilter = '';
  genreFilter = '';
  typeFilter = '';
  minPagesFilter: number | null = null;
  minQuantityFilter: number | null = null;
  statusFilter = '';

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
    return this.authUtils.isLibrarian();
  }

  get isUser(): boolean {
    return this.authUtils.isUser();
  }

  showNotification(message: string, color: string) {
    this.notification = {message, color};
    setTimeout(() => this.notification = null, 3000);
  }

  reserveBook(bookId?: number) {
    if (bookId === undefined) return;
    const jwt = localStorage.getItem('jwt');
    if (!jwt) return;
    const user = this.authUtils.parseJwt(jwt);
    if (!user.userId) return;
    this.bookService.reserveBook({bookId, userId: user.userId}).subscribe({
      next: () => this.showNotification('Reservation successful!', 'green'),
      error: () => this.showNotification('Failed to reserve book.', 'red')
    });
  }
}
