import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BookListComponent} from './components/book-list/book-list.component';
import {UserListComponent} from './components/user-list/user-list.component';
import {ReservationListComponent} from './components/reservation-list/reservation-list.component';
import {BookFormComponent} from './components/book-form/book-form.component';
import {UserFormComponent} from './components/user-form/user-form.component';
import {ReservationFormComponent} from './components/reservation-form/reservation-form.component';

const routes: Routes = [
  {path: '', redirectTo: 'books', pathMatch: 'full'},
  {path: 'books', component: BookListComponent},
  {path: 'books/new', component: BookFormComponent},
  {path: 'users', component: UserListComponent},
  {path: 'users/new', component: UserFormComponent},
  {path: 'reservations', component: ReservationListComponent},
  {path: 'reservations/new', component: ReservationFormComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
