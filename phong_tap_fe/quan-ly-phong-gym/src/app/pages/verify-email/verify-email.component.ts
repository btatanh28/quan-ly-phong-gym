import { CommonModule } from '@angular/common';
import {
  Component,
  HostListener,
  Input,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
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

  ngOnChanges(changes: SimpleChanges) {
    if (changes['email'] && !changes['email'].firstChange) {
      this.myForm?.get('email')?.setValue(this.email);
    }
  }

  private initForm() {
    this.myForm = this.fb.group({
      otp1: [''],
      otp2: [''],
      otp3: [''],
      otp4: [''],
      otp5: [''],
      otp6: [''],
      email: [this.email],
    });
  }

  moveNext(event: any, index: number) {
    const input = event.target;
    const value = input.value;

    if (value.length === 1 && index < 6) {
      const nextInput = input.parentElement.children[index] as HTMLInputElement;
      nextInput.focus();
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
