import {
  Component,
  EventEmitter,
  forwardRef,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'input-date',
  templateUrl: './input-date.component.html',
  styleUrls: ['./input-date.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputDateComponent),
      multi: true,
    },
  ],
})
export class InputDateComponent implements ControlValueAccessor {
  @Input() value?: number;
  @Output() valueChange = new EventEmitter<number>();
  disabled = false;

  private onChange = (_: any) => {};
  private onTouched = () => {};

  writeValue(value: any): void {
    this.value = value ?? null;
  }
  registerOnChange(fn: any): void {
    this.onChange = fn;
  }
  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }
  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  onDateChange(event: any) {
    const dateStr = event.target.value; // "2025-12-11"
    if (!dateStr) return;

    const [y, m, d] = dateStr.split('-');
    const longValue = Number(`${y}${m}${d}`);

    this.value = longValue;
    this.onChange(this.value);
    this.onTouched();
  }

  get dateTimeString(): string {
    if (!this.value) return '';

    const str = this.value.toString();
    if (str.length !== 8) return '';

    const yyyy = str.slice(0, 4);
    const mm = str.slice(4, 6);
    const dd = str.slice(6, 8);

    return `${yyyy}-${mm}-${dd}`;
  }
}
