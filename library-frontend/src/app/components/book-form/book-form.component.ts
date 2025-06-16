import {Component, inject} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BookService} from '../../services/book.service';
import {Book, BookGenre, BookStatus, BookType} from '../../models/book.model';
import {FormValidationService} from '../../services/form-validation.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.scss']
})
export class BookFormComponent {
  private bookService = inject(BookService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private formValidation = inject(FormValidationService);
  private translate = inject(TranslateService);

  book: Book = {
    id: 0,
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

  // enum values
  bookStatuses = Object.values(BookStatus);
  bookGenres = Object.values(BookGenre);
  bookTypes = Object.values(BookType);

  notification: { message: string, color: string } | null = null;

  getValidationMessage(control: any, fieldName: string): string {
    if (!control || !control.touched) return '';
    return this.formValidation.getValidationMessage(control, fieldName);
  }

  onSubmit(): void {
    this.bookService.createBook(this.book).subscribe({
      next: () => {
        this.showNotification(this.translate.instant('books.notifications.createSuccess'), 'green');
        this.goBack();
      },
      error: () => this.showNotification(this.translate.instant('books.notifications.createError'), 'red')
    });
  }

  goBack(): void {
    this.router.navigate(['/books']);
  }

  showNotification(message: string, color: string) {
    this.notification = {message, color};
    setTimeout(() => this.notification = null, 3000);
  }
}
