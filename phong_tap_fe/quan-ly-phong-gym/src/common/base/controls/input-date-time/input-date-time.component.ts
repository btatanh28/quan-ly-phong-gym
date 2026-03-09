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
  constructor() {}
  writeValue(obj: any): void {
    throw new Error('Method not implemented.');
  }
  registerOnChange(fn: any): void {
    throw new Error('Method not implemented.');
  }
  registerOnTouched(fn: any): void {
    throw new Error('Method not implemented.');
  }
  setDisabledState?(isDisabled: boolean): void {
    throw new Error('Method not implemented.');
  }

  onDateChange(event: any) {
    const dateTime = new Date(event.target.value);

    this.value = dateTime.getTime();

    this.valueChange.emit(this.value);
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
    const ss = pad(d.getSeconds());

    return `${yyyy}-${mm}-${dd}T${hh}:${min}:${ss}`;
  }
}
