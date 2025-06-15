import {Pipe, PipeTransform} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Pipe({
  name: 'enumFormat',
  pure: false
})
export class EnumFormatPipe implements PipeTransform {
  constructor(private translate: TranslateService) {
  }

  transform(value: string): string {
    if (!value) return '';

    // Try to get translation for reservation status
    const statusTranslation = this.translate.instant(`enums.reservationStatus.${value}`);
    if (statusTranslation !== `enums.reservationStatus.${value}`) {
      return statusTranslation;
    }

    // Try to get translation for user role
    const roleTranslation = this.translate.instant(`enums.userRole.${value}`);
    if (roleTranslation !== `enums.userRole.${value}`) {
      return roleTranslation;
    }

    // Try to get translation for book status
    const bookStatusTranslation = this.translate.instant(`enums.bookStatus.${value}`);
    if (bookStatusTranslation !== `enums.bookStatus.${value}`) {
      return bookStatusTranslation;
    }

    // Try to get translation for book genre
    const bookGenreTranslation = this.translate.instant(`enums.bookGenre.${value}`);
    if (bookGenreTranslation !== `enums.bookGenre.${value}`) {
      return bookGenreTranslation;
    }

    // Try to get translation for book type
    const bookTypeTranslation = this.translate.instant(`enums.bookType.${value}`);
    if (bookTypeTranslation !== `enums.bookType.${value}`) {
      return bookTypeTranslation;
    }

    // Fallback to default formatting if no translation found
    let formatted = value.replace(/_/g, ' ');
    formatted = formatted.toLowerCase()
      .split(' ')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');

    return formatted;
  }
}
