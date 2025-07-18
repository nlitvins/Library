import {Component, inject, OnInit} from '@angular/core';
import {BookService} from '../../services/book.service';
import {Book, BookGenre, BookStatus, BookType} from '../../models/book.model';
import {AuthUtilsService} from '../../services/auth-utils.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {
  private bookService = inject(BookService);
  private authUtils = inject(AuthUtilsService);
  private translate = inject(TranslateService);

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

  reserveBook(bookId: number) {
    const jwt = localStorage.getItem('jwt');
    if (!jwt) return;
    const user = this.authUtils.parseJwt(jwt);
    if (!user.userId) return;
    this.bookService.reserveBook({bookId, userId: user.userId}).subscribe({
      next: () => this.showNotification(this.translate.instant('books.notifications.reserveSuccess'), 'green'),
      error: () => this.showNotification(this.translate.instant('books.notifications.reserveError'), 'red')
    });
  }

  changeBookStatus(bookId: number) {
    const scrollPosition = window.scrollY;
    this.bookService.changeBookStatus(bookId).subscribe({
      next: () => {
        this.showNotification(this.translate.instant('books.notifications.statusChangeSuccess'), 'green')
        this.books = this.books.map(book => book.id === bookId ? {
          ...book,
          status: book.status === BookStatus.AVAILABLE ? BookStatus.NOT_AVAILABLE : BookStatus.AVAILABLE
        } : book);
        this.applyFilters();
        window.scrollTo(0, scrollPosition);
      },
      error: () => this.showNotification(this.translate.instant('books.notifications.statusChangeError'), 'red')
    });
  }
}
