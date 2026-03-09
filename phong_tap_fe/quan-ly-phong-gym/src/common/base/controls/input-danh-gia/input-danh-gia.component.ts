import { NgFor } from '@angular/common';
import { Component, forwardRef, Input, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'input-danh-gia',
  imports: [NgFor],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputDanhGiaComponent),
      multi: true,
    },
  ],
  templateUrl: './input-danh-gia.component.html',
  styleUrls: ['./input-danh-gia.component.css'],
})
export class InputDanhGiaComponent implements ControlValueAccessor {
  @Input() max = 5;

  rating = 0;
  hovered = 0;

  private onChange = (_: any) => {};
  private onTouched = () => {};

  writeValue(value: number): void {
    this.rating = value || 0;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setRating(value: number) {
    this.rating = value;
    this.onChange(value);
    this.onTouched();
  }

  hoverRating(value: number) {
    this.hovered = value;
  }

  resetHover() {
    this.hovered = 0;
  }
}
