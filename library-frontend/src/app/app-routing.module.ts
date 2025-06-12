import {inject, NgModule} from '@angular/core';
import {Router, RouterModule, Routes} from '@angular/router';
import {BookListComponent} from './components/book-list/book-list.component';
import {UserListComponent} from './components/user-list/user-list.component';
import {ReservationListComponent} from './components/reservation-list/reservation-list.component';
import {BookFormComponent} from './components/book-form/book-form.component';

const authGuard = () => {
  const router = inject(Router);
  const jwt = localStorage.getItem('jwt');
  if (jwt) return true;
  return router.createUrlTree(['/books']);
};

const routes: Routes = [
  {path: '', redirectTo: 'books', pathMatch: 'full'},
  {path: 'books', component: BookListComponent},
  {path: 'books/new', component: BookFormComponent, canActivate: [authGuard]},
  {path: 'users', component: UserListComponent, canActivate: [authGuard]},
  {path: 'reservations', component: ReservationListComponent, canActivate: [authGuard]},
  {path: 'my-reservations', component: ReservationListComponent, canActivate: [authGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
