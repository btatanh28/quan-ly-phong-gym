import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import Swal from 'sweetalert2';
import { VerifyEmailComponent } from '../../verify-email/verify-email.component';
import { AuthService } from '../../../../common/shared/service/application/authService';
import { ExtentionService } from '../../../../common/base/service/extention.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, VerifyEmailComponent, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  public myForm?: FormGroup;
  public showVerify = false;
  public emailForVerify!: string;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private ex: ExtentionService,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      tenKhachHang: [null],
      matKhau: [null],
      xacNhanMatKhau: [null],
      email: [null],
      diaChi: [null],
      soDienThoai: [null],
    });
  }

  async onRegisterSubmit() {
    if (
      this.myForm?.get('soDienThoai')?.value.length > 10 ||
      this.myForm?.get('soDienThoai')?.value.length <= 9
    ) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Số điện thoại không hợp lệ',
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    }

    const req = {
      ...this.myForm?.getRawValue(),
    };

    setTimeout(() => {
      this.showVerify = true;
    }, 2500);

    const response = await firstValueFrom(this.authService.registerClient(req));

    this.emailForVerify = this.myForm?.get('email')?.value;

    if (response) {
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Đăng ký thành công',
        text: 'Vui lòng kiểm tra email để nhận mã xác nhận.',
        showConfirmButton: false,
        timer: 3500,
      });
    }
  }
}
