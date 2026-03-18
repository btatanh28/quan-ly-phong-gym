import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  HostListener,
  Input,
  OnInit,
  QueryList,
  SimpleChanges,
  ViewChildren,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import Swal from 'sweetalert2';
import { Route, Router } from '@angular/router';
import { FormModule } from '../../../common/module/forms.module';
import { VerifyService } from '../../../common/shared/service/application/verifyServer';

@Component({
  selector: 'app-verify-email',
  standalone: true,
  imports: [CommonModule, FormModule],
  templateUrl: './verify-email.component.html',
  styleUrls: ['./verify-email.component.css'],
})
export class VerifyEmailComponent implements OnInit {
  @ViewChildren('otpInput') inputs!: QueryList<ElementRef>;
  @Input() email!: string;
  public myForm?: FormGroup;
  public isScrolled = false;

  constructor(
    private verifyService: VerifyService,
    private fb: FormBuilder,
    private router: Router,
  ) {}

  @HostListener('window:scroll', [])
  onWindowScroll() {
    this.isScrolled = window.pageYOffset > 50;
  }

  ngOnInit() {
    this.initForm();
  }

  ngAfterViewInit() {
    this.inputs.first.nativeElement.focus();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['email'] && !changes['email'].firstChange) {
      this.myForm?.get('email')?.setValue(this.email);
    }
  }

  private initForm() {
    this.myForm = this.fb.group({
      otp1: ['', Validators.required],
      otp2: ['', Validators.required],
      otp3: ['', Validators.required],
      otp4: ['', Validators.required],
      otp5: ['', Validators.required],
      otp6: ['', Validators.required],
      email: [this.email],
    });
  }

  onInput(event: any, index: number) {
    const value = event.target.value;

    // 👉 paste nhiều ký tự
    if (value.length > 1) {
      this.handlePaste(value);
      return;
    }

    // 👉 nhập bình thường → sang ô tiếp
    if (value && index < 5) {
      this.inputs.toArray()[index + 1].nativeElement.focus();
    }
  }

  onKeyDown(event: KeyboardEvent, index: number) {
    const input = event.target as HTMLInputElement;

    if (event.key === 'Backspace') {
      if (!input.value && index > 0) {
        const prev = this.inputs.toArray()[index - 1].nativeElement;
        prev.focus();
        prev.value = '';
        this.setFormValue(index - 1, '');
      }
    }
  }

  setFormValue(index: number, value: string) {
    const controlName = `otp${index + 1}`;
    this.myForm?.get(controlName)?.setValue(value);
  }

  handlePaste(value: string) {
    const chars = value.replace(/\D/g, '').slice(0, 6).split('');

    chars.forEach((char, i) => {
      const input = this.inputs.toArray()[i].nativeElement;
      input.value = char;
      this.setFormValue(i, char);
    });

    const lastIndex = chars.length - 1;
    if (lastIndex >= 0) {
      this.inputs.toArray()[lastIndex].nativeElement.focus();
    }
  }

  onPaste(event: ClipboardEvent) {
    event.preventDefault();

    const pasteData = event.clipboardData?.getData('text') || '';

    const chars = pasteData.replace(/\D/g, '').slice(0, 6).split('');

    chars.forEach((char, i) => {
      const input = this.inputs.toArray()[i].nativeElement;
      input.value = char;
      this.setFormValue(i, char);
    });

    if (chars.length === 6) {
      setTimeout(() => this.verifySubmit(), 3000);
    } else {
      const lastIndex = chars.length - 1;
      if (lastIndex >= 0) {
        this.inputs.toArray()[lastIndex].nativeElement.focus();
      }
    }
  }

  async verifySubmit() {
    if (!this.myForm?.valid) {
      Swal.fire({
        icon: 'warning',
        title: 'Vui lòng nhập đầy đủ 6 chữ số',
        timer: 2000,
        showConfirmButton: false,
      });
      return;
    }

    const maXacNhan =
      this.myForm?.value.otp1 +
      this.myForm?.value.otp2 +
      this.myForm?.value.otp3 +
      this.myForm?.value.otp4 +
      this.myForm?.value.otp5 +
      this.myForm?.value.otp6;

    const req = {
      maXacNhan: maXacNhan,
      email: this.myForm?.value.email,
    };

    const response = await firstValueFrom(this.verifyService.verifyEmail(req));

    if (response) {
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Kích hoạt tài khoản thành công!',
        showConfirmButton: false,
        timer: 2000,
      });
      this.router.navigate(['/home'], { queryParams: { tab: 'login' } });
    }
  }
}
