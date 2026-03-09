import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import {
  Component,
  EventEmitter,
  forwardRef,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { FormModule } from '../../../module/forms.module';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'input-select-api',
  standalone: true,
  imports: [CommonModule, FormModule],
  templateUrl: './input-select-api.component.html',
  styleUrls: ['./input-select-api.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputSelectApiComponent),
      multi: true,
    },
  ],
})
export class InputSelectApiComponent implements OnInit, ControlValueAccessor {
  @Input() apiService: any;
  @Input() actionName: any = 'getCombobox';

  @Output() onChange = new EventEmitter<any>();

  options: any[] = [];
  value: any = null;
  disabled = false;

  async ngOnInit() {
    await this.loadOptions();
  }

  async loadOptions() {
    this.apiService[this.actionName]().subscribe((res: any) => {
      this.options = res.items ?? res;
    });
  }

  // ===== Reactive Form Support =====
  writeValue(val: any) {
    this.value = val;
  }

  registerOnChange(fn: any) {
    this.propagateChange = fn;
  }

  touched = () => {};
  registerOnTouched(fn: any) {
    this.touched = fn;
  }

  setDisabledState(isDisabled: boolean) {
    this.disabled = isDisabled;
  }

  propagateChange = (_: any) => {};

  // ===== User Select =====
  handleChange(val: any) {
    // this.value = event.target.value;

    // this.propagateChange(this.value);
    // this.onChange.emit(this.value);

    this.value = val;

    this.propagateChange(val);

    this.touched();
    this.onChange.emit(val);
  }
}
