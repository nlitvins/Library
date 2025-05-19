import {Component, OnInit} from '@angular/core';
import {Book, BookService} from '../../services/book.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.scss']
})
export class BookFormComponent implements OnInit {
  book: Book = {title: '', author: '', quantity: 1};
  loading = false;
  error: string | null = null;

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
