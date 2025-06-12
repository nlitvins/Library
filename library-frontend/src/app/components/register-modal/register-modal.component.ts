import {Component, EventEmitter, inject, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {FormValidationService} from '../../services/form-validation.service';

@Component({
  selector: 'app-register-modal',
  templateUrl: './register-modal.component.html',
  styleUrls: ['./register-modal.component.scss']
})
export class RegisterModalComponent {
  @Output() modalClose = new EventEmitter<void>();
  @Output() registerSuccess = new EventEmitter<void>();

  private fb = inject(FormBuilder);
  private userService = inject(UserService);
  private formValidation = inject(FormValidationService);

  registerForm: FormGroup;
  error: string | null = null;

  constructor() {
    this.registerForm = this.fb.group({
      email: ['', [this.formValidation.getRequiredValidator(), this.formValidation.getEmailValidator()]],
      password: ['', [this.formValidation.getRequiredValidator(), this.formValidation.getMinLengthValidator(6)]],
      name: ['', this.formValidation.getRequiredValidator()],
      secondName: ['', this.formValidation.getRequiredValidator()],
      userName: ['', [this.formValidation.getRequiredValidator(), this.formValidation.getMinLengthValidator(3)]],
      mobileNumber: ['', [this.formValidation.getRequiredValidator(), this.formValidation.getPhoneValidator()]],
      personCode: ['', [this.formValidation.getRequiredValidator(), this.formValidation.getPersonCodeValidator()]]
    });
  }

  getValidationMessage(controlName: string): string {
    const control = this.registerForm.get(controlName);
    if (!control) return '';
    return this.formValidation.getValidationMessage(control, controlName);
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const userData = {
        ...this.registerForm.value
      };

      this.userService.registerUser(userData).subscribe({
        next: () => {
          this.registerForm.reset();
          this.error = null;
          this.registerSuccess.emit();
          this.modalClose.emit();
        },
        error: (error: { error?: { message?: string } }) => {
          this.error = error.error?.message ?? 'Registration failed. Please try again.';
        }
      });
    }
  }
}
