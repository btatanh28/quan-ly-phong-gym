import {
  Component,
  EventEmitter,
  forwardRef,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'input-moneny',
  templateUrl: './input-moneny.component.html',
  styleUrls: ['./input-moneny.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputMonenyComponent),
      multi: true,
    },
  ],
})
export class InputMonenyComponent {
  @Input() value: number | null = null;
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

  onMonenyChange(event: any) {
    let val = event.target.value;

    // Xóa hết ký tự không phải số
    val = val.replace(/\D/g, '');

    // Chuyển về number
    const num = val ? parseInt(val, 10) : null;

    this.value = num;
    event.target.value = this.monenyString;
    this.onChange(this.value);
    this.onTouched();
  }

  get monenyString(): string {
    if (this.value == null) return '';
    return this.value.toLocaleString('vi-VN'); // 5000000 -> 5.000.000
  }
}
