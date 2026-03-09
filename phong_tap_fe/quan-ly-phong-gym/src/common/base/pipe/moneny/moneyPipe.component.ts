import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'moneyPipe',
  standalone: true,
})
export class MoneyPipe implements PipeTransform {
  transform(value: number | string): string {
    if (value == null) return '';
    const numberValue = typeof value === 'string' ? parseFloat(value) : value;

    return numberValue.toLocaleString('vi-VN') + 'đ';
  }
}
