import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BookListComponent} from './components/book-list/book-list.component';
import {UserListComponent} from './components/user-list/user-list.component';
import {ReservationListComponent} from './components/reservation-list/reservation-list.component';
import {BookFormComponent} from './components/book-form/book-form.component';
import {UserFormComponent} from './components/user-form/user-form.component';
import {ReservationFormComponent} from './components/reservation-form/reservation-form.component';

@NgModule({
  declarations: [
    AppComponent,
    BookListComponent,
    UserListComponent,
    ReservationListComponent,
    BookFormComponent,
    UserFormComponent,
    ReservationFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
