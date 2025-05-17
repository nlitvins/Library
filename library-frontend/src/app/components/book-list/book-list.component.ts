import {Component, OnInit} from '@angular/core';
import {BookService, Book} from '../../services/book.service';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {
  books: Book[] = [];

  constructor(private bookService: BookService) {
  }

  ngOnInit(): void {
    this.bookService.getBooks().subscribe(data => this.books = data);
  }
}
