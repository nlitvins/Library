import {Injectable} from '@angular/core';
import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';

type ValidationMessageKey = 'required' | 'email' | 'minlength' | 'maxlength' | 'pattern' | 'min' | 'max' | 'dateRange';
type ValidationMessage = string | ((value: any) => string);

@Injectable({
  providedIn: 'root'
})
export class FormValidationService {
  // Common validation patterns
  private readonly PHONE_PATTERN = '^[0-9]{8,12}$';
  private readonly PERSON_CODE_PATTERN = '^[0-9]{11}$';
  private readonly EMAIL_PATTERN = '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$';

  // Common validation messages
  readonly VALIDATION_MESSAGES: Record<ValidationMessageKey, ValidationMessage> = {
    required: 'This field is required',
    email: 'Please enter a valid email address',
    minlength: (min: number) => `Must be at least ${min} characters`,
    maxlength: (max: number) => `Must be at most ${max} characters`,
    pattern: (pattern: string) => {
      switch (pattern) {
        case this.PHONE_PATTERN:
          return 'Please enter a valid mobile number (8-12 digits)';
        case this.PERSON_CODE_PATTERN:
          return 'Personal code must be 11 digits';
        case this.EMAIL_PATTERN:
          return 'Please enter a valid email address';
        default:
          return 'Invalid format';
      }
    },
    min: (min: number) => `Must be at least ${min}`,
    max: (max: number) => `Must be at most ${max}`,
    dateRange: 'End date must be after start date'
  };

  // Common validators
  getRequiredValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      return control.value ? null : {required: true};
    };
  }

  getEmailValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) return null;
      const pattern = new RegExp(this.EMAIL_PATTERN);
      return pattern.test(control.value) ? null : {email: true};
    };
  }

  getPhoneValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) return null;
      const pattern = new RegExp(this.PHONE_PATTERN);
      return pattern.test(control.value) ? null : {pattern: this.PHONE_PATTERN};
    };
  }

  getPersonCodeValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) return null;
      const pattern = new RegExp(this.PERSON_CODE_PATTERN);
      return pattern.test(control.value) ? null : {pattern: this.PERSON_CODE_PATTERN};
    };
  }

  getMinLengthValidator(minLength: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) return null;
      return control.value.length >= minLength ? null : {minlength: {requiredLength: minLength}};
    };
  }

  getMaxLengthValidator(maxLength: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) return null;
      return control.value.length <= maxLength ? null : {maxlength: {requiredLength: maxLength}};
    };
  }

  getMinValueValidator(min: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) return null;
      const value = Number(control.value);
      return value >= min ? null : {min: {min}};
    };
  }

  getMaxValueValidator(max: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) return null;
      const value = Number(control.value);
      return value <= max ? null : {max: {max}};
    };
  }

  getDateRangeValidator(startDateControl: AbstractControl): ValidatorFn {
    return (endDateControl: AbstractControl): ValidationErrors | null => {
      if (!startDateControl.value || !endDateControl.value) return null;
      const startDate = new Date(startDateControl.value);
      const endDate = new Date(endDateControl.value);
      return endDate >= startDate ? null : {dateRange: true};
    };
  }

  // Helper method to get validation message
  getValidationMessage(control: AbstractControl, fieldName: string): string {
    if (!control.errors || !control.touched) return '';

    const errorKey = Object.keys(control.errors)[0] as ValidationMessageKey;
    const error = control.errors[errorKey];
    const message = this.VALIDATION_MESSAGES[errorKey];

    if (typeof message === 'function') {
      if (errorKey === 'pattern') {
        return message(error);
      }
      if (errorKey === 'minlength' || errorKey === 'maxlength') {
        return message(error.requiredLength);
      }
      if (errorKey === 'min' || errorKey === 'max') {
        return message(error[errorKey]);
      }
    }

    return message as string || `${fieldName} is invalid`;
  }
}
