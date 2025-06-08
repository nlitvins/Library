import {Component, OnInit} from '@angular/core';
import {Book, BookGenre, BookService, BookStatus, BookType} from '../../services/book.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.scss']
})
export class BookFormComponent implements OnInit {
  book: Book = {
    title: '',
    author: '',
    quantity: 1,
    creationYear: new Date().toISOString().split('T')[0],
    status: BookStatus.AVAILABLE,
    genre: BookGenre.CLASSIC,
    pages: 0,
    edition: '',
    releaseDate: new Date().toISOString().split('T')[0],
    type: BookType.BOOK
  };
  loading = false;
  error: string | null = null;
  bookStatuses = Object.values(BookStatus);
  bookGenres = Object.values(BookGenre);
  bookTypes = Object.values(BookType);

  constructor(private bookService: BookService, private router: Router) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.loading = true;
    this.error = null;
    this.bookService.addBook(this.book).subscribe({
      next: () => this.router.navigate(['/books']),
      error: err => {
        this.error = 'Failed to create book.';
        this.loading = false;
      }
    });
  }
}
