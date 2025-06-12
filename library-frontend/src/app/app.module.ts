import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BookListComponent} from './components/book-list/book-list.component';
import {UserListComponent} from './components/user-list/user-list.component';
import {ReservationListComponent} from './components/reservation-list/reservation-list.component';
import {BookFormComponent} from './components/book-form/book-form.component';
import {LoginModalComponent} from './components/login-modal/login-modal.component';
import {RegisterModalComponent} from './components/register-modal/register-modal.component';
import {AuthInterceptor} from './interceptors/auth.interceptor';
import {EnumFormatPipe} from './pipes/enum-format.pipe';

@NgModule({
  declarations: [
    AppComponent,
    BookListComponent,
    UserListComponent,
    ReservationListComponent,
    BookFormComponent,
    LoginModalComponent,
    RegisterModalComponent,
    EnumFormatPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
