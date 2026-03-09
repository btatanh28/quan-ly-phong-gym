import { NgFor, NgIf } from '@angular/common';
import {
  Component,
  forwardRef,
  Input,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { FormModule } from '../../../module/forms.module';

@Component({
  selector: 'input-select',
  standalone: true,
  imports: [NgFor, FormModule],
  templateUrl: './input-select.component.html',
  styleUrls: ['./input-select.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputSelectComponent),
      multi: true,
    },
  ],
})
export class InputSelectComponent implements OnInit {
  @Input() label?: string;
  @Input() items: { value: number; label: string }[] = [];

  value: any;
  disabled = false;

  onChange = (_: any) => {};
  onTouched = () => {};

  ngOnInit(): void {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['items'] && this.items) {
      this.options = this.items;
      // Nếu đã có value trước đó, đảm bảo select hiển thị đúng
      if (this.value != null) {
        const exists = this.options.find((o) => o.value === this.value);
        if (!exists) {
          this.value = null; // fallback về Tất cả nếu không có trong options
        }
      }
    }
  }

  options: { value: number; label: string }[] = [];

  writeValue(value: any): void {
    this.value = value ?? null;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  handleChange(val: any) {
    this.value = Number(val);
    this.onChange(this.value);
    this.onTouched();
  }
}
