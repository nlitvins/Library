import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'enumFormat'
})
export class EnumFormatPipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return '';

    // Replace underscores with spaces
    let formatted = value.replace(/_/g, ' ');

    // Convert to title case
    formatted = formatted.toLowerCase()
      .split(' ')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');

    return formatted;
  }
}
