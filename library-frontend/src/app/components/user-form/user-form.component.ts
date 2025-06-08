import {Component, OnInit} from '@angular/core';
import {CreateUserRequest, UserService} from '../../services/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {
  user: CreateUserRequest = {
    name: '',
    secondName: '',
    email: '',
    mobileNumber: '',
    personCode: ''
  };
  loading = false;
  error: string | null = null;

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.loading = true;
    this.error = null;
    this.userService.registerUser(this.user).subscribe({
      next: () => this.router.navigate(['/users']),
      error: err => {
        this.error = 'Failed to create user.';
        this.loading = false;
      }
    });
  }
}
