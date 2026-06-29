import {
  Component,
  EventEmitter,
  forwardRef,
  Input,
  OnInit,
  Output,
  output,
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'input-date-time',
  standalone: true,
  templateUrl: './input-date-time.component.html',
  styleUrls: ['./input-date-time.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputDateTimeComponent),
      multi: true,
    },
  ],
})
export class InputDateTimeComponent implements ControlValueAccessor {
  @Input() value?: number;
  @Output() valueChange = new EventEmitter<number>();
  disabled = false;

  constructor() {}

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
    const dateStr = event.target.value;

    if (!dateStr) return;

    const date = new Date(dateStr);

    const timestamp = date.getTime();

    this.value = timestamp;

    this.onChange(this.value);

    this.onTouched();
  }

  get dateTimeString(): string {
    if (!this.value) return '';

    const d = new Date(this.value);

    const pad = (n: number) => n.toString().padStart(2, '0');

    const yyyy = d.getFullYear();
    const mm = pad(d.getMonth() + 1);
    const dd = pad(d.getDate());

    const hh = pad(d.getHours());
    const min = pad(d.getMinutes());

    return `${yyyy}-${mm}-${dd}T${hh}:${min}`;
  }
}
